package com.adelsonsljunior.simpleecommerce.core.domain.adapters.services;

import com.adelsonsljunior.simpleecommerce.core.domain.Product;
import com.adelsonsljunior.simpleecommerce.core.domain.SaleProduct;
import com.adelsonsljunior.simpleecommerce.core.domain.User;
import com.adelsonsljunior.simpleecommerce.core.domain.Sale;
import com.adelsonsljunior.simpleecommerce.core.domain.dtos.sale.SaleRequestDTO;
import com.adelsonsljunior.simpleecommerce.core.domain.dtos.sale.SaleResponseDTO;
import com.adelsonsljunior.simpleecommerce.core.domain.ports.repositories.ProductRepositoryPort;
import com.adelsonsljunior.simpleecommerce.core.domain.ports.repositories.SaleRepositoryPort;
import com.adelsonsljunior.simpleecommerce.core.domain.ports.repositories.UserRepositoryPort;
import com.adelsonsljunior.simpleecommerce.core.domain.ports.services.SaleServicePort;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import java.time.LocalDate;
import java.time.temporal.IsoFields;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SaleServiceImp implements SaleServicePort {

    private final SaleRepositoryPort saleRepository;
    private final ProductRepositoryPort productRepository;
    private final UserRepositoryPort userRepository;

    public SaleServiceImp(SaleRepositoryPort saleRepository, ProductRepositoryPort productRepository, UserRepositoryPort userRepository) {
        this.saleRepository = saleRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @CacheEvict(value = "sales", allEntries = true)
    @Override
    public SaleResponseDTO create(SaleRequestDTO saleRequest) {

        User user = this.userRepository.findById(saleRequest.userId());

        Sale sale = new Sale();
        sale.setUser(user);
        sale.setSaleDate(saleRequest.saleDate());

        List<SaleProduct> saleProducts = saleRequest.saleProducts().
                stream()
                .map(saleProductDTO -> {

                    Product product = this.productRepository.findById(saleProductDTO.productId());

                    this.productRepository.decrementStock(product.getId(), saleProductDTO.quantity());

                    // Cria um SaleProduct para cada produto na venda
                    SaleProduct saleProduct = new SaleProduct();
                    saleProduct.setProduct(product);
                    saleProduct.setQuantity(saleProductDTO.quantity());
                    saleProduct.setSale(sale); // Associar a venda ao SaleProduct

                    return saleProduct;
                })
                .collect(Collectors.toList());

        double totalAmount = calculateTotalAmount(saleProducts);

        sale.setSaleProducts(saleProducts);
        sale.setTotalAmount(totalAmount);

        Sale createdSale = this.saleRepository.create(sale);
        return createdSale.toSaleResponseDTO();
    }

    private double calculateTotalAmount(List<SaleProduct> saleProducts) {
        return saleProducts.stream()
                .mapToDouble(sp -> sp.getProduct().getPrice() * sp.getQuantity())
                .sum();
    }

    @Cacheable(value = "sales")
    @Override
    public List<SaleResponseDTO> findAll() {
        List<Sale> sales = this.saleRepository.findAll();
        System.out.println("O SERVICE PEGOU NO SERVICE");
        return sales.stream()
                .map(Sale::toSaleResponseDTO)
                .collect(Collectors.toList());
    }

    @Cacheable(value = "products", key = "#id")
    @Override
    public SaleResponseDTO findById(Long saleId) {
        Sale foundSale = this.saleRepository.findById(saleId);
        return foundSale.toSaleResponseDTO();
    }

    @CacheEvict(value = "sales", key = "#id")
    @Override
    public void delete(Long saleId) {
        this.saleRepository.delete(saleId);
    }

    @CacheEvict(value = "sales", allEntries = true)
    @Override
    public SaleResponseDTO update(Long saleId, SaleRequestDTO saleRequest) {

        Sale sale = this.saleRepository.findById(saleId);

        User user = this.userRepository.findById(saleRequest.userId());
        sale.setUser(user);
        sale.setSaleDate(saleRequest.saleDate());

        // Mapeia os produtos da venda existente em um mapa para comparação
        Map<Long, SaleProduct> existingSaleProducts = sale.getSaleProducts().stream()
                .collect(Collectors.toMap(
                        sp -> sp.getProduct().getId(),
                        sp -> sp,
                        (sp1, sp2) -> sp1)); // Resolve duplicatas mantendo o primeiro

        List<SaleProduct> updatedSaleProducts = saleRequest.saleProducts().stream()
                .map(saleProductDTO -> {
                    Product product = this.productRepository.findById(saleProductDTO.productId());

                    // Busca a quantidade antiga do produto na venda existente
                    SaleProduct existingSaleProduct = existingSaleProducts.get(product.getId());
                    int oldQuantity = existingSaleProduct != null ? existingSaleProduct.getQuantity() : 0;
                    int newQuantity = saleProductDTO.quantity();

                    // Se a quantidade do produto aumentou, decrementa apenas a parte aumentada
                    if (newQuantity > oldQuantity) {
                        int increment = newQuantity - oldQuantity;
                        this.productRepository.decrementStock(product.getId(), increment);
                    }
                    // Se a quantidade do produto diminuiu, incrementa o estoque com a parte liberada
                    else if (newQuantity < oldQuantity) {
                        int decrement = oldQuantity - newQuantity;
                        this.productRepository.incrementStock(product.getId(), decrement);
                    }

                    SaleProduct saleProduct = existingSaleProduct != null ? existingSaleProduct : new SaleProduct();
                    saleProduct.setProduct(product);
                    saleProduct.setQuantity(newQuantity);
                    saleProduct.setSale(sale); // Reassocia a venda ao SaleProduct

                    return saleProduct;
                })
                .collect(Collectors.toList());

        double totalAmount = calculateTotalAmount(updatedSaleProducts);

        sale.setSaleProducts(updatedSaleProducts);
        sale.setTotalAmount(totalAmount);

        Sale updatedSale = this.saleRepository.create(sale);

        return updatedSale.toSaleResponseDTO();
    }


    @Override
    public List<SaleResponseDTO> findByDate(LocalDate date) {

        List<Sale> sales = this.saleRepository.findByDate(date);
        return sales.stream()
                .map(Sale::toSaleResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<SaleResponseDTO> findByMonth(int month, int year) {

        List<Sale> sales = this.saleRepository.findByMonth(month, year);
        return sales.stream()
                .map(Sale::toSaleResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<SaleResponseDTO> findByCurrentWeek() {

        LocalDate now = LocalDate.now();
        int year = now.getYear();
        int week = now.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);

        List<Sale> sales = this.saleRepository.findByCurrentWeek(year, week);
        return sales.stream()
                .map(Sale::toSaleResponseDTO)
                .collect(Collectors.toList());
    }

}
