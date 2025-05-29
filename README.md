# ProjectR API - Sistema de Mensajería Instantánea

[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.4.5-green.svg)]()
[![Java](https://img.shields.io/badge/Java-24-blue.svg)]()
[![MySQL](https://img.shields.io/badge/MySQL-8.0-orange.svg)]()
[![WebSocket](https://img.shields.io/badge/WebSocket-STOMP-brightgreen.svg)]()

API backend para una aplicación de mensajería instantánea desarrollada como proyecto final de curso. Ofrece funcionalidades completas de autenticación, gestión de contactos y chat en tiempo real.

## Características Principales

- 🔐 Autenticación JWT segura
- 👥 Gestión completa de contactos (agregar, bloquear, eliminar)
- 💬 Chat en tiempo real con WebSocket
- 📱 Generación automática de números telefónicos únicos
- 📚 Historial de mensajes persistente
- 👤 Gestión de perfiles de usuario (foto, contraseña)
- 🔄 Sincronización de estado en tiempo real (conexión/desconexión)

## Tecnologías Utilizadas

- **Backend**: 
  - Spring Boot 3.4.5
  - Spring Security
  - Spring Data JPA
  - Spring WebSocket
- **Base de datos**: MySQL
- **Autenticación**: JWT (JSON Web Tokens)
- **Herramientas**:
  - Maven
  - Lombok
  - JJWT

## Diagrama de Arquitectura

```mermaid
graph TD
    A[Cliente] -->|HTTP/WebSocket| B[Spring Boot]
    B --> C[Spring Security]
    B --> D[Spring WebSocket]
    B --> E[Spring Data JPA]
    E --> F[MySQL Database]
    D --> G[Broker de Mensajes]
    C --> H[JWT Authentication]
