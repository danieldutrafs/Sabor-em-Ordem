DROP DATABASE sabor_em_ordem;
CREATE DATABASE IF NOT EXISTS sabor_em_ordem;
USE sabor_em_ordem;

CREATE TABLE categoria (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome_categoria VARCHAR(25) NOT NULL
);

CREATE TABLE produtos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome_produto VARCHAR(50) NOT NULL,
    quantidade INT NOT NULL,
    categoria_id BIGINT, -- Corrigido para BIGINT
    preco DOUBLE NOT NULL,
    CONSTRAINT fk_categoria FOREIGN KEY (categoria_id) REFERENCES categoria(id)
);

CREATE TABLE clientes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY, -- Corrigido de INT para BIGINT
    nome_cliente VARCHAR(50) NOT NULL,
    telefone VARCHAR(15) NOT NULL,
    endereco VARCHAR(150) NOT NULL
);

CREATE TABLE encomenda (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cliente_id BIGINT NOT NULL,
    data_entrega DATE NOT NULL,
    status_encomenda VARCHAR(255) NOT NULL,
    valor_entrada DOUBLE NOT NULL,
    frete DOUBLE NOT NULL,
    destinatario VARCHAR(255) NOT NULL,
    endereco VARCHAR(255) NOT NULL,
    is_retirada BOOLEAN NOT NULL,
    forma_pagamento VARCHAR(255) NOT NULL,
    CONSTRAINT fk_cliente_encomenda FOREIGN KEY (cliente_id) REFERENCES clientes(id)
);

CREATE TABLE itens_venda (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    encomenda_id BIGINT NOT NULL, 
    produto_id BIGINT NOT NULL,
    quantidade INT NOT NULL,
    preco_momento DOUBLE NOT NULL,
    CONSTRAINT fk_encomenda_itens FOREIGN KEY (encomenda_id) REFERENCES encomenda(id),
    CONSTRAINT fk_produto_itens FOREIGN KEY (produto_id) REFERENCES produtos(id)
);

CREATE TABLE movimentacao_estoque (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    tipo ENUM('entrada','saída') NOT NULL,
    quantidade INT NOT NULL,
    produto_id BIGINT NOT NULL,
    CONSTRAINT fk_movimentacao_produto FOREIGN KEY (produto_id) REFERENCES produtos(id)
);