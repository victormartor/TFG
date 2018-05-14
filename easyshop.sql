-- phpMyAdmin SQL Dump
-- version 4.6.6
-- https://www.phpmyadmin.net/
--
-- Servidor: localhost
-- Tiempo de generación: 14-05-2018 a las 15:50:42
-- Versión del servidor: 5.7.17-log
-- Versión de PHP: 5.6.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `easyshop`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `articulo`
--

CREATE TABLE `articulo` (
  `Id` int(11) NOT NULL,
  `Nombre` varchar(256) COLLATE utf8_spanish_ci NOT NULL,
  `PVP` double NOT NULL,
  `Id_Categoria` int(11) NOT NULL,
  `Talla_Es_Numero` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;


-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `articulo_color`
--

CREATE TABLE `articulo_color` (
  `Id_Articulo` int(11) NOT NULL,
  `Id_Color` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;


-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `articulo_color_imagen`
--

CREATE TABLE `articulo_color_imagen` (
  `Id_Articulo` int(11) NOT NULL,
  `Id_Color` int(11) NOT NULL,
  `Id_Imagen` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;


-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `articulo_talla`
--

CREATE TABLE `articulo_talla` (
  `Id_Articulo` int(11) NOT NULL,
  `Id_Talla` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;


-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `art_combina_con`
--

CREATE TABLE `art_combina_con` (
  `Id_Articulo1` int(11) NOT NULL,
  `Id_Articulo2` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `categoria`
--

CREATE TABLE `categoria` (
  `Id` int(11) NOT NULL,
  `Nombre` varchar(256) COLLATE utf8_spanish_ci NOT NULL,
  `Id_Imagen` int(11) NOT NULL,
  `Id_Marca` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;


-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `color`
--

CREATE TABLE `color` (
  `Id` int(11) NOT NULL,
  `Nombre` varchar(128) COLLATE utf8_spanish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `color`
--

INSERT INTO `color` (`Id`, `Nombre`) VALUES
(-1, 'Nuevo color');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `imagen`
--

CREATE TABLE `imagen` (
  `Id` int(11) NOT NULL,
  `Nombre` varchar(256) COLLATE utf8_spanish_ci NOT NULL,
  `Ruta` varchar(256) COLLATE utf8_spanish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `imagen`
--

INSERT INTO `imagen` (`Id`, `Nombre`, `Ruta`) VALUES
(-1, 'Imagen vacía', '');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `marca`
--

CREATE TABLE `marca` (
  `Id` int(11) NOT NULL,
  `Nombre` varchar(256) COLLATE utf8_spanish_ci NOT NULL,
  `Id_Imagen` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;


-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `stock`
--

CREATE TABLE `stock` (
  `Id_Articulo` int(11) NOT NULL,
  `Id_Talla` int(11) NOT NULL,
  `Id_Color` int(11) NOT NULL,
  `Existencias` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `talla`
--

CREATE TABLE `talla` (
  `Id` int(11) NOT NULL,
  `Nombre` varchar(128) COLLATE utf8_spanish_ci NOT NULL,
  `Es_Numero` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `talla`
--

INSERT INTO `talla` (`Id`, `Nombre`, `Es_Numero`) VALUES
(1, 'XXS', 0),
(2, 'XS', 0),
(3, 'S', 0),
(4, 'M', 0),
(5, 'L', 0),
(6, 'XL', 0),
(7, 'XXL', 0),
(8, '3XL', 0),
(9, '30', 1),
(10, '31', 1),
(11, '32', 1),
(12, '33', 1),
(13, '34', 1),
(14, '35', 1),
(15, '36', 1),
(16, '37', 1),
(17, '38', 1),
(18, '39', 1),
(19, '40', 1),
(20, '41', 1),
(21, '42', 1),
(22, '43', 1),
(23, '44', 1),
(24, '45', 1),
(25, '46', 1);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `articulo`
--
ALTER TABLE `articulo`
  ADD PRIMARY KEY (`Id`),
  ADD KEY `Id_Categoria` (`Id_Categoria`);

--
-- Indices de la tabla `articulo_color`
--
ALTER TABLE `articulo_color`
  ADD KEY `Id_Articulo` (`Id_Articulo`,`Id_Color`),
  ADD KEY `Id_Color` (`Id_Color`);

--
-- Indices de la tabla `articulo_color_imagen`
--
ALTER TABLE `articulo_color_imagen`
  ADD KEY `Id_Articulo` (`Id_Articulo`,`Id_Color`),
  ADD KEY `Id_Imagen` (`Id_Imagen`),
  ADD KEY `Id_Color` (`Id_Color`);

--
-- Indices de la tabla `articulo_talla`
--
ALTER TABLE `articulo_talla`
  ADD KEY `Id_Articulo` (`Id_Articulo`,`Id_Talla`),
  ADD KEY `Id_Talla` (`Id_Talla`);

--
-- Indices de la tabla `art_combina_con`
--
ALTER TABLE `art_combina_con`
  ADD KEY `Id_Articulo1` (`Id_Articulo1`,`Id_Articulo2`),
  ADD KEY `Id_Articulo2` (`Id_Articulo2`);

--
-- Indices de la tabla `categoria`
--
ALTER TABLE `categoria`
  ADD PRIMARY KEY (`Id`),
  ADD KEY `Id_Imagen` (`Id_Imagen`),
  ADD KEY `Id_Marca` (`Id_Marca`);

--
-- Indices de la tabla `color`
--
ALTER TABLE `color`
  ADD PRIMARY KEY (`Id`);

--
-- Indices de la tabla `imagen`
--
ALTER TABLE `imagen`
  ADD PRIMARY KEY (`Id`);

--
-- Indices de la tabla `marca`
--
ALTER TABLE `marca`
  ADD PRIMARY KEY (`Id`),
  ADD KEY `Id_Imagen` (`Id_Imagen`);

--
-- Indices de la tabla `stock`
--
ALTER TABLE `stock`
  ADD KEY `Id_Articulo` (`Id_Articulo`,`Id_Talla`,`Id_Color`),
  ADD KEY `Id_Color` (`Id_Color`),
  ADD KEY `Id_Talla` (`Id_Talla`);

--
-- Indices de la tabla `talla`
--
ALTER TABLE `talla`
  ADD PRIMARY KEY (`Id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `articulo`
--
ALTER TABLE `articulo`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `categoria`
--
ALTER TABLE `categoria`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `color`
--
ALTER TABLE `color`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `imagen`
--
ALTER TABLE `imagen`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `marca`
--
ALTER TABLE `marca`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `talla`
--
ALTER TABLE `talla`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT;
--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `articulo`
--
ALTER TABLE `articulo`
  ADD CONSTRAINT `articulo_ibfk_1` FOREIGN KEY (`Id_Categoria`) REFERENCES `categoria` (`Id`);

--
-- Filtros para la tabla `articulo_color`
--
ALTER TABLE `articulo_color`
  ADD CONSTRAINT `articulo_color_ibfk_1` FOREIGN KEY (`Id_Color`) REFERENCES `color` (`Id`),
  ADD CONSTRAINT `articulo_color_ibfk_2` FOREIGN KEY (`Id_Articulo`) REFERENCES `articulo` (`Id`);

--
-- Filtros para la tabla `articulo_color_imagen`
--
ALTER TABLE `articulo_color_imagen`
  ADD CONSTRAINT `articulo_color_imagen_ibfk_1` FOREIGN KEY (`Id_Articulo`) REFERENCES `articulo` (`Id`),
  ADD CONSTRAINT `articulo_color_imagen_ibfk_2` FOREIGN KEY (`Id_Color`) REFERENCES `color` (`Id`),
  ADD CONSTRAINT `articulo_color_imagen_ibfk_3` FOREIGN KEY (`Id_Imagen`) REFERENCES `imagen` (`Id`);

--
-- Filtros para la tabla `articulo_talla`
--
ALTER TABLE `articulo_talla`
  ADD CONSTRAINT `articulo_talla_ibfk_1` FOREIGN KEY (`Id_Talla`) REFERENCES `talla` (`Id`),
  ADD CONSTRAINT `articulo_talla_ibfk_2` FOREIGN KEY (`Id_Articulo`) REFERENCES `articulo` (`Id`);

--
-- Filtros para la tabla `art_combina_con`
--
ALTER TABLE `art_combina_con`
  ADD CONSTRAINT `art_combina_con_ibfk_1` FOREIGN KEY (`Id_Articulo1`) REFERENCES `articulo` (`Id`),
  ADD CONSTRAINT `art_combina_con_ibfk_2` FOREIGN KEY (`Id_Articulo2`) REFERENCES `articulo` (`Id`);

--
-- Filtros para la tabla `categoria`
--
ALTER TABLE `categoria`
  ADD CONSTRAINT `categoria_ibfk_1` FOREIGN KEY (`Id_Marca`) REFERENCES `marca` (`Id`),
  ADD CONSTRAINT `categoria_ibfk_2` FOREIGN KEY (`Id_Imagen`) REFERENCES `imagen` (`Id`);

--
-- Filtros para la tabla `marca`
--
ALTER TABLE `marca`
  ADD CONSTRAINT `marca_ibfk_1` FOREIGN KEY (`Id_Imagen`) REFERENCES `imagen` (`Id`);

--
-- Filtros para la tabla `stock`
--
ALTER TABLE `stock`
  ADD CONSTRAINT `stock_ibfk_1` FOREIGN KEY (`Id_Articulo`) REFERENCES `articulo` (`Id`),
  ADD CONSTRAINT `stock_ibfk_2` FOREIGN KEY (`Id_Color`) REFERENCES `color` (`Id`),
  ADD CONSTRAINT `stock_ibfk_3` FOREIGN KEY (`Id_Talla`) REFERENCES `talla` (`Id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
