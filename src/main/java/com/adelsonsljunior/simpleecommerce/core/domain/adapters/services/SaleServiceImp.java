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

    @Override
    public List<SaleResponseDTO> findAll() {
        List<Sale> sales = this.saleRepository.findAll();
        return sales.stream()
                .map(Sale::toSaleResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public SaleResponseDTO findById(Long id) {
        Sale foundSale = this.saleRepository.findById(id);
        return foundSale.toSaleResponseDTO();
    }

    @Override
    public void delete(Long id) {
        this.saleRepository.delete(id);
    }

    @Override
    public SaleResponseDTO update(Long id, SaleRequestDTO saleRequest) {

        Sale sale = this.saleRepository.findById(id);

        User user = this.userRepository.findById(saleRequest.userId());

        sale.setUser(user);
        sale.setSaleDate(saleRequest.saleDate());

        // Mapeia os produtos da venda existente em um mapa para comparação
        Map<Long, SaleProduct> existingSaleProducts = sale.getSaleProducts().stream()
                .collect(Collectors.toMap(sp -> sp.getProduct().getId(), sp -> sp));

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
                        if (product.getStock() < increment) {
                            throw new IllegalArgumentException("Insufficient stock for product: " + product.getName());
                        }
                        this.productRepository.decrementStock(product.getId(), increment);
                    }
                    // Se a quantidade do produto diminuiu, incrementa o estoque com a parte liberada
                    else if (newQuantity < oldQuantity) {
                        int decrement = oldQuantity - newQuantity;
                        this.productRepository.incrementStock(product.getId(), decrement);
                    }

                    // Cria ou atualiza um SaleProduct
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
}
