-- phpMyAdmin SQL Dump
-- version 4.8.3
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le :  mar. 18 déc. 2018 à 13:16
-- Version du serveur :  5.7.23
-- Version de PHP :  7.2.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données :  `hellocine`
--

-- --------------------------------------------------------

--
-- Structure de la table `cinema`
--

DROP TABLE IF EXISTS `cinema`;
CREATE TABLE IF NOT EXISTS `cinema` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) COLLATE utf8_unicode_ci NOT NULL,
  `address` varchar(500) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Déchargement des données de la table `cinema`
--

INSERT INTO `cinema` (`id`, `name`, `address`) VALUES
(4, 'Pathe', 'Boulogne'),
(5, 'Landowski', 'Boulogne'),
(6, 'UGC', 'Noisy-Le-Grand'),
(7, 'Le Bijou', 'Noisy-Le-Grand'),
(8, 'L\'archipel', 'Paris'),
(9, 'Studio Galande', 'Paris'),
(10, 'Centre Georges-Pompidou', 'Paris'),
(11, 'Vaise', 'Lyon'),
(12, 'Multiplex Carre de Soie', 'Lyon');

-- --------------------------------------------------------

--
-- Structure de la table `movie`
--

DROP TABLE IF EXISTS `movie`;
CREATE TABLE IF NOT EXISTS `movie` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `description` varchar(1000) COLLATE utf8_unicode_ci DEFAULT NULL,
  `rate` int(11) DEFAULT NULL,
  `type` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `actor` varchar(200) COLLATE utf8_unicode_ci NOT NULL,
  `producer` varchar(200) COLLATE utf8_unicode_ci NOT NULL,
  `length` int(11) NOT NULL,
  `country` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `on_screen_date` date NOT NULL,
  `language` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `image` varchar(5000) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Déchargement des données de la table `movie`
--

INSERT INTO `movie` (`id`, `name`, `description`, `rate`, `type`, `actor`, `producer`, `length`, `country`, `on_screen_date`, `language`, `image`) VALUES
(3, 'Astérix - Le Secret de la Potion Magique', 'À la suite d’une chute lors de la cueillette du gui, le druide Panoramix décide qu’il est temps d’assurer l’avenir du village. Accompagné d’Astérix et Obélix, il entreprend de parcourir le monde gaulois à la recherche d’un jeune druide talentueux à qui transmettre le Secret de la Potion Magique… ', 4, 'Animation, famille', 'Bernard Alane, Christian Clavier, Guillaume Briat', 'Louis Clichy, Alexandre Astier', 90, 'France', '2018-12-05', 'french', 'http://img.over-blog-kiwi.com/0/95/30/84/20180601/ob_69372a_e9e872bb-fd60-483e-b14d-ab2c1f37d604.jpg'),
(4, 'Rémi sans famille', 'Les aventures du jeune Rémi, orphelin recueilli par la douce Madame Barberin. A l’âge de 10 ans, il est arraché à sa mère adoptive et confié au Signor Vitalis, un mystérieux musicien ambulant. A ses côtés, il va apprendre la rude vie de saltimbanque et à chanter pour gagner son pain.  ', 4, 'Aventure, Famille', 'Daniel Auteuil, Maleaume Paquin, Virginie Ledoyen', 'Antoine Blossier', 120, 'France', '2018-12-12', 'french', 'https://cdn-s-www.lejsl.com/images/cd5fd5e0-1866-4a85-b0ed-7f4a29e67daa/BES_06/illustration-remi-sans-famille-avec-les-petits-chanteurs-a-la-croix-de-bois_1-1542970294.jpg'),
(5, 'Mortal Engines', 'Des centaines d’années après qu’un évènement apocalyptique a détruit la Terre, l’humanité s’est adaptée pour survivre en trouvant un nouveau mode de vie. Ainsi, de gigantesques villes mobiles errent sur Terre prenant sans pitié le pouvoir sur d’autres villes mobiles plus petites. ', 3, 'Science fiction, Aventure, Action', 'Hera Hilmar, Robert Sheehan, Hugo Weaving', 'Christian Rivers', 120, 'Us', '2018-12-12', 'english', 'http://fr.web.img6.acsta.net/r_1280_720/pictures/18/11/29/16/57/0851659.jpg'),
(6, 'Pupille', 'Théo est remis à l\'adoption par sa mère biologique le jour de sa naissance. C\'est un accouchement sous X. La mère à deux mois pour revenir sur sa décision...ou pas. Les services de l\'aide sociale à l\'enfance et le service adoption se mettent en mouvement. Les uns doivent s\'occuper du bébé, le porter (au sens plein du terme) dans ce temps suspendu, cette phase d\'incertitude. Les autres doivent trouver celle qui deviendra sa mère adoptante. Elle s\'appelle Alice et cela fait dix ans qu\'elle se bat pour avoir un enfant. PUPILLE est l\'histoire de la rencontre entre Alice, 41 ans, et Théo, trois mois.', 4, 'Drame', 'Sandrine Kiberlain, Gilles Lellouche, Élodie Bouchez', 'Jeanne Herry', 120, 'France', '2018-12-05', 'french', 'http://fr.web.img6.acsta.net/r_1280_720/pictures/18/08/29/19/16/0550992.jpg'),
(8, 'test', 'test', 1, 'test', 'test', 'test', 30, 'test', '2018-12-16', 'test', 'https://cdn.pixabay.com/photo/2013/07/12/12/58/tv-test-pattern-146649_960_720.png');

-- --------------------------------------------------------

--
-- Structure de la table `session`
--

DROP TABLE IF EXISTS `session`;
CREATE TABLE IF NOT EXISTS `session` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `movie_id` int(11) NOT NULL,
  `cinema_id` int(11) NOT NULL,
  `hour` time NOT NULL,
  `date` date NOT NULL,
  `version` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `session_ibfk_1` (`movie_id`),
  KEY `session_ibfk_2` (`cinema_id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Déchargement des données de la table `session`
--

INSERT INTO `session` (`id`, `movie_id`, `cinema_id`, `hour`, `date`, `version`) VALUES
(4, 3, 10, '10:00:00', '2018-12-20', 'vf'),
(5, 3, 10, '14:00:00', '2018-12-20', 'vf'),
(6, 5, 8, '16:00:00', '2018-12-26', 'vo'),
(7, 6, 7, '18:00:00', '2018-12-15', 'vf'),
(8, 4, 4, '14:00:00', '2018-12-19', 'vf'),
(9, 4, 11, '12:00:00', '2018-12-20', 'vf'),
(10, 5, 4, '10:00:00', '2018-12-25', 'vf'),
(11, 6, 5, '18:00:00', '2018-12-19', 'vf'),
(12, 4, 11, '19:00:00', '2018-12-27', 'vf');

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `session`
--
ALTER TABLE `session`
  ADD CONSTRAINT `session_ibfk_1` FOREIGN KEY (`movie_id`) REFERENCES `movie` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `session_ibfk_2` FOREIGN KEY (`cinema_id`) REFERENCES `cinema` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
