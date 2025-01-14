USE [master]
GO
/****** Object:  Database [test4]    Script Date: 7/12/2021 11:20:05 AM ******/
CREATE DATABASE [test4]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'test4', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL15.SQLEXPRESS\MSSQL\DATA\test4.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'test4_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL15.SQLEXPRESS\MSSQL\DATA\test4_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
 WITH CATALOG_COLLATION = DATABASE_DEFAULT
GO
ALTER DATABASE [test4] SET COMPATIBILITY_LEVEL = 150
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [test4].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [test4] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [test4] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [test4] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [test4] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [test4] SET ARITHABORT OFF 
GO
ALTER DATABASE [test4] SET AUTO_CLOSE ON 
GO
ALTER DATABASE [test4] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [test4] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [test4] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [test4] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [test4] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [test4] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [test4] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [test4] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [test4] SET  ENABLE_BROKER 
GO
ALTER DATABASE [test4] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [test4] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [test4] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [test4] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [test4] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [test4] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [test4] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [test4] SET RECOVERY SIMPLE 
GO
ALTER DATABASE [test4] SET  MULTI_USER 
GO
ALTER DATABASE [test4] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [test4] SET DB_CHAINING OFF 
GO
ALTER DATABASE [test4] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [test4] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [test4] SET DELAYED_DURABILITY = DISABLED 
GO
ALTER DATABASE [test4] SET ACCELERATED_DATABASE_RECOVERY = OFF  
GO
ALTER DATABASE [test4] SET QUERY_STORE = OFF
GO
USE [test4]
GO
/****** Object:  Table [dbo].[Administrator]    Script Date: 7/12/2021 11:20:05 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Administrator](
	[Username] [varchar](50) NOT NULL,
	[Password] [varchar](20) NULL,
	[NameEmp] [nvarchar](50) NULL,
	[Gender] [nvarchar](10) NULL,
	[Birthday] [varchar](20) NULL,
	[Phone] [varchar](20) NULL,
	[Email] [varchar](50) NULL,
	[Address] [nvarchar](max) NULL,
	[Salary] [varchar](50) NULL,
	[workdate] [varchar](50) NULL,
	[stand] [nvarchar](50) NULL,
	[price] [nvarchar](50) NULL,
	[job] [nvarchar](50) NULL,
 CONSTRAINT [PK__Administ__536C85E56CD438D9] PRIMARY KEY CLUSTERED 
(
	[Username] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Customer]    Script Date: 7/12/2021 11:20:06 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Customer](
	[IDCus] [int] IDENTITY(100000,1) NOT NULL,
	[IdentityCard] [varchar](20) NOT NULL,
	[CusName] [nvarchar](50) NULL,
	[DateAdd] [varchar](20) NULL,
	[Phone] [varchar](20) NULL,
	[Email] [varchar](50) NULL,
	[Quantity] [int] NULL,
	[Discount] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[IDCus] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Employee]    Script Date: 7/12/2021 11:20:06 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Employee](
	[UsernameEmp] [varchar](50) NOT NULL,
	[Password] [varchar](20) NULL,
	[NameEmp] [nvarchar](50) NULL,
	[Gender] [nvarchar](10) NULL,
	[Birthday] [varchar](20) NULL,
	[Phone] [varchar](20) NULL,
	[Email] [varchar](50) NULL,
	[Address] [nvarchar](max) NULL,
	[Salary] [varchar](50) NULL,
	[workdate] [varchar](50) NULL,
	[stand] [nvarchar](50) NULL,
	[price] [nvarchar](50) NOT NULL,
	[Job] [nvarchar](50) NULL,
 CONSTRAINT [PK__Employee__B868CA96BF8B1E13] PRIMARY KEY CLUSTERED 
(
	[UsernameEmp] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Order]    Script Date: 7/12/2021 11:20:06 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Order](
	[IDOrder] [varchar](20) NOT NULL,
	[DateOrder] [varchar](20) NULL,
	[TimeOrder] [varchar](20) NULL,
	[UsernameEmp] [varchar](50) NULL,
PRIMARY KEY CLUSTERED 
(
	[IDOrder] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[OrderDetails]    Script Date: 7/12/2021 11:20:06 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[OrderDetails](
	[IDOrder] [varchar](20) NOT NULL,
	[IDProduct] [varchar](20) NOT NULL,
	[CusName] [nvarchar](50) NULL,
	[Quantity] [int] NULL,
	[NamePromo] [nvarchar](50) NULL,
 CONSTRAINT [PK_OrderDetails] PRIMARY KEY CLUSTERED 
(
	[IDOrder] ASC,
	[IDProduct] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Product]    Script Date: 7/12/2021 11:20:06 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Product](
	[IDProduct] [varchar](20) NOT NULL,
	[ProductName] [nvarchar](100) NULL,
	[IDType] [varchar](10) NULL,
	[Price] [int] NULL,
	[code] [nvarchar](10) NULL,
	[acc] [nvarchar](50) NULL,
PRIMARY KEY CLUSTERED 
(
	[IDProduct] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[ProductType]    Script Date: 7/12/2021 11:20:06 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ProductType](
	[IDType] [varchar](10) NOT NULL,
	[TypeName] [nvarchar](50) NULL,
	[Size] [nvarchar](10) NULL,
PRIMARY KEY CLUSTERED 
(
	[IDType] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Promotions]    Script Date: 7/12/2021 11:20:06 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Promotions](
	[IDPromo] [int] IDENTITY(1,1) NOT NULL,
	[NamePromo] [nvarchar](50) NOT NULL,
	[DiscountPromo] [int] NULL,
	[StartPromo] [varchar](20) NULL,
	[EndPromo] [varchar](20) NULL,
	[Description] [nvarchar](max) NULL,
PRIMARY KEY CLUSTERED 
(
	[IDPromo] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Revenue]    Script Date: 7/12/2021 11:20:06 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Revenue](
	[IDRevenue] [int] IDENTITY(1,1) NOT NULL,
	[Date] [varchar](20) NULL,
	[Money] [varchar](20) NULL,
PRIMARY KEY CLUSTERED 
(
	[IDRevenue] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Store]    Script Date: 7/12/2021 11:20:06 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Store](
	[code] [nvarchar](10) NOT NULL,
	[name] [nvarchar](50) NOT NULL,
	[phone] [nvarchar](10) NOT NULL,
	[email] [nvarchar](50) NOT NULL,
	[address] [nvarchar](50) NOT NULL,
 CONSTRAINT [PK_Store] PRIMARY KEY CLUSTERED 
(
	[code] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
INSERT [dbo].[Administrator] ([Username], [Password], [NameEmp], [Gender], [Birthday], [Phone], [Email], [Address], [Salary], [workdate], [stand], [price], [job]) VALUES (N'admin', N'YWRtaW4=', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL)
INSERT [dbo].[Administrator] ([Username], [Password], [NameEmp], [Gender], [Birthday], [Phone], [Email], [Address], [Salary], [workdate], [stand], [price], [job]) VALUES (N'long152', N'MTIzNDU2', N'long', N'Nam', N'14/07/1999', N'0125896321', N'long1@gmail.com', N'Tiền Giang', N'9,030,000 VNÐ', N'30', N'300,000', N'30000', N'Quản lý')
INSERT [dbo].[Administrator] ([Username], [Password], [NameEmp], [Gender], [Birthday], [Phone], [Email], [Address], [Salary], [workdate], [stand], [price], [job]) VALUES (N'sakura1', N'MTIzNDU2', N'sakura', N'Nam', N'23/07/1998', N'0258796431', N'sakura@gmail.com', N'Làng lá', N'2,010,000 VNÐ', N'10', N'200,000', N'10000', N'Quản lý')
INSERT [dbo].[Administrator] ([Username], [Password], [NameEmp], [Gender], [Birthday], [Phone], [Email], [Address], [Salary], [workdate], [stand], [price], [job]) VALUES (N'sasuke2', N'MTIzNDU2', N'sasuke1', N'Nam', N'09/07/1999', N'0123478565', N'sasuke2@gmail.com', N'Làng lá', N'6,030,000 VNÐ', N'20', N'300,000', N'30000', N'Quản lý')
GO
SET IDENTITY_INSERT [dbo].[Customer] ON 

INSERT [dbo].[Customer] ([IDCus], [IdentityCard], [CusName], [DateAdd], [Phone], [Email], [Quantity], [Discount]) VALUES (100000, N'122261551', N'Bale', N'24/6/2021', N'01212692802', N'Bale@gmail.com', 26, 10)
INSERT [dbo].[Customer] ([IDCus], [IdentityCard], [CusName], [DateAdd], [Phone], [Email], [Quantity], [Discount]) VALUES (100001, N'122261552', N'Maldini', N'24/6/2021', N'01212692802', N'Maldini@gmail.com', 25, 10)
INSERT [dbo].[Customer] ([IDCus], [IdentityCard], [CusName], [DateAdd], [Phone], [Email], [Quantity], [Discount]) VALUES (100002, N'122261553', N'Ramos', N'24/6/2021', N'01212692802', N'Ramos@gmail.com', 19, 5)
INSERT [dbo].[Customer] ([IDCus], [IdentityCard], [CusName], [DateAdd], [Phone], [Email], [Quantity], [Discount]) VALUES (100003, N'122261554', N'Contouris', N'24/6/2021', N'01212692802', N'Contouris@gmail.com', 10, 5)
INSERT [dbo].[Customer] ([IDCus], [IdentityCard], [CusName], [DateAdd], [Phone], [Email], [Quantity], [Discount]) VALUES (100004, N'12312323123', N'Ronaldo', N'16/06/2021', N'0286845986', N'dsas@gmail.com', 20, 15)
INSERT [dbo].[Customer] ([IDCus], [IdentityCard], [CusName], [DateAdd], [Phone], [Email], [Quantity], [Discount]) VALUES (100005, N'2313213213', N'dasdasasd', N'16/06/2021', N'02132131221', N'w12312@gmail.com', 30, 20)
INSERT [dbo].[Customer] ([IDCus], [IdentityCard], [CusName], [DateAdd], [Phone], [Email], [Quantity], [Discount]) VALUES (100007, N'3123213213', N'dasdas1', N'16/06/2021', N'05616565255', N'dsd@gmail.com', 5, 2)
INSERT [dbo].[Customer] ([IDCus], [IdentityCard], [CusName], [DateAdd], [Phone], [Email], [Quantity], [Discount]) VALUES (100008, N'123213213', N'sas1', N'17/06/2021', N'0516126942', N'tu@gmail.com', 10, 6)
INSERT [dbo].[Customer] ([IDCus], [IdentityCard], [CusName], [DateAdd], [Phone], [Email], [Quantity], [Discount]) VALUES (100009, N'123456789', N'Vo Thanh ML', N'20/06/2021', N'0123456987', N'mk@gmail.com', 15, 5)
INSERT [dbo].[Customer] ([IDCus], [IdentityCard], [CusName], [DateAdd], [Phone], [Email], [Quantity], [Discount]) VALUES (100010, N'123498765', N'Messi', N'23/06/2021', N'0958762143', N'messi@gmail.com', 20, 8)
SET IDENTITY_INSERT [dbo].[Customer] OFF
GO
INSERT [dbo].[Employee] ([UsernameEmp], [Password], [NameEmp], [Gender], [Birthday], [Phone], [Email], [Address], [Salary], [workdate], [stand], [price], [Job]) VALUES (N'davidbeck', N'OTg3NjU0', N'David Beckham', N'Nam', N'23/05/1995', N'0222222222', N'david@gmail.com', N'Brooklyn,American', N'12,200,000 VNÐ', N'30', N'400,000', N'200000', N'Nhân  viên')
INSERT [dbo].[Employee] ([UsernameEmp], [Password], [NameEmp], [Gender], [Birthday], [Phone], [Email], [Address], [Salary], [workdate], [stand], [price], [Job]) VALUES (N'long152', N'MTIzNDU2', N'long', N'Nam', N'14/07/1999', N'0125896321', N'long1@gmail.com', N'Tiền Giang', N'9,030,000 VNÐ', N'30', N'300,000', N'30000', N'Quản lý')
INSERT [dbo].[Employee] ([UsernameEmp], [Password], [NameEmp], [Gender], [Birthday], [Phone], [Email], [Address], [Salary], [workdate], [stand], [price], [Job]) VALUES (N'marcelo', N'MTIzNDU2', N'Marcelo', N'Nam', N'20/02/1987', N'01227971919', N'marcelo@gmail.com', N'Brazil', N'12,100,800 VNÐ', N'30', N'400,000', N'150000', N'Nhân  viên')
INSERT [dbo].[Employee] ([UsernameEmp], [Password], [NameEmp], [Gender], [Birthday], [Phone], [Email], [Address], [Salary], [workdate], [stand], [price], [Job]) VALUES (N'mbappe', N'123456', N'MBappe', N'Nam', N'08/07/1999', N'0124566789', N'mbappe@gmail.com', N'France', N'9,300,300 VNÐ', N'30', N'300,000', N'300000', N'Nhân  viên')
INSERT [dbo].[Employee] ([UsernameEmp], [Password], [NameEmp], [Gender], [Birthday], [Phone], [Email], [Address], [Salary], [workdate], [stand], [price], [Job]) VALUES (N'naruto1', N'MTIzNDU2', N'naruto', N'Nam', N'10/07/1992', N'01256988745', N'naruto1@gmail.com', N'Làng lá', N'4,010,000 VNÐ', N'20', N'200,000', N'10000', N'Nhân  viên')
INSERT [dbo].[Employee] ([UsernameEmp], [Password], [NameEmp], [Gender], [Birthday], [Phone], [Email], [Address], [Salary], [workdate], [stand], [price], [Job]) VALUES (N'ronaldocris', N'lD????d?B?EA*', N'Cristiano Ronaldo', N'Nam', N'08/05/1986', N'0932212333', N'ronaldo@gmail.com', N'Portugal', N'12,100,000 VNÐ', N'30', N'400,000', N'200000', N'Nhân  viên')
INSERT [dbo].[Employee] ([UsernameEmp], [Password], [NameEmp], [Gender], [Birthday], [Phone], [Email], [Address], [Salary], [workdate], [stand], [price], [Job]) VALUES (N'sakura1', N'MTIzNDU2', N'sakura', N'Nam', N'23/07/1998', N'0258796431', N'sakura@gmail.com', N'Làng lá', N'2,010,000 VNÐ', N'10', N'200,000', N'10000', N'Nhân  viên')
INSERT [dbo].[Employee] ([UsernameEmp], [Password], [NameEmp], [Gender], [Birthday], [Phone], [Email], [Address], [Salary], [workdate], [stand], [price], [Job]) VALUES (N'sasuke1', N'TVRJek5EVTI=', N'sasuke', N'Nam', N'09/07/1999', N'0123478569', N'sasuke1@gmail.com', N'Làng lá', N'6,030,000 VNÐ', N'20', N'300,000', N'30000', N'Quản lý')
INSERT [dbo].[Employee] ([UsernameEmp], [Password], [NameEmp], [Gender], [Birthday], [Phone], [Email], [Address], [Salary], [workdate], [stand], [price], [Job]) VALUES (N'sasuke2', N'MTIzNDU2', N'sasuke1', N'Nam', N'09/07/1999', N'0123478565', N'sasuke2@gmail.com', N'Làng lá', N'6,030,000 VNÐ', N'20', N'300,000', N'30000', N'Quản lý')
INSERT [dbo].[Employee] ([UsernameEmp], [Password], [NameEmp], [Gender], [Birthday], [Phone], [Email], [Address], [Salary], [workdate], [stand], [price], [Job]) VALUES (N'sergioramos', N'987654', N'Sergio Ramos', N'Nam', N'04/09/1985', N'0124221177', N'ramos@gmail.com', N'Spain', N'12,300,000 VNÐ', N'30', N'400,000', N'300000', N'Nhân  viên')
GO
INSERT [dbo].[Order] ([IDOrder], [DateOrder], [TimeOrder], [UsernameEmp]) VALUES (N'HD0008', N'23/06/2021', N'22:07:36', N'davidbeck')
INSERT [dbo].[Order] ([IDOrder], [DateOrder], [TimeOrder], [UsernameEmp]) VALUES (N'HD1200', N'26/06/2021', N'17:26:25', N'davidbeck')
INSERT [dbo].[Order] ([IDOrder], [DateOrder], [TimeOrder], [UsernameEmp]) VALUES (N'HD1205', N'04/07/2021', N'23:14:42', N'davidbeck')
INSERT [dbo].[Order] ([IDOrder], [DateOrder], [TimeOrder], [UsernameEmp]) VALUES (N'HD1256', N'01/07/2021', N'21:45:33', N'davidbeck')
INSERT [dbo].[Order] ([IDOrder], [DateOrder], [TimeOrder], [UsernameEmp]) VALUES (N'HD1289', N'04/07/2021', N'23:29:35', N'davidbeck')
INSERT [dbo].[Order] ([IDOrder], [DateOrder], [TimeOrder], [UsernameEmp]) VALUES (N'HD1546', N'04/07/2021', N'23:58:55', N'davidbeck')
INSERT [dbo].[Order] ([IDOrder], [DateOrder], [TimeOrder], [UsernameEmp]) VALUES (N'HD4586', N'04/07/2021', N'23:57:15', N'davidbeck')
INSERT [dbo].[Order] ([IDOrder], [DateOrder], [TimeOrder], [UsernameEmp]) VALUES (N'HD5688', N'04/07/2021', N'23:53:51', N'davidbeck')
INSERT [dbo].[Order] ([IDOrder], [DateOrder], [TimeOrder], [UsernameEmp]) VALUES (N'HD5689', N'04/07/2021', N'23:52:38', N'davidbeck')
INSERT [dbo].[Order] ([IDOrder], [DateOrder], [TimeOrder], [UsernameEmp]) VALUES (N'HD7542', N'05/07/2021', N'00:01:18', N'davidbeck')
INSERT [dbo].[Order] ([IDOrder], [DateOrder], [TimeOrder], [UsernameEmp]) VALUES (N'HD7896', N'04/07/2021', N'23:59:57', N'davidbeck')
INSERT [dbo].[Order] ([IDOrder], [DateOrder], [TimeOrder], [UsernameEmp]) VALUES (N'HD7897', N'05/07/2021', N'18:39:33', N'davidbeck')
INSERT [dbo].[Order] ([IDOrder], [DateOrder], [TimeOrder], [UsernameEmp]) VALUES (N'HD7898', N'05/07/2021', N'21:35:19', N'davidbeck')
INSERT [dbo].[Order] ([IDOrder], [DateOrder], [TimeOrder], [UsernameEmp]) VALUES (N'HD7899', N'05/07/2021', N'22:13:44', N'davidbeck')
INSERT [dbo].[Order] ([IDOrder], [DateOrder], [TimeOrder], [UsernameEmp]) VALUES (N'HD7900', N'05/07/2021', N'22:15:00', N'davidbeck')
INSERT [dbo].[Order] ([IDOrder], [DateOrder], [TimeOrder], [UsernameEmp]) VALUES (N'HD7901', N'06/07/2021', N'19:40:18', N'davidbeck')
INSERT [dbo].[Order] ([IDOrder], [DateOrder], [TimeOrder], [UsernameEmp]) VALUES (N'HD7902', N'07/07/2021', N'09:52:21', N'davidbeck')
INSERT [dbo].[Order] ([IDOrder], [DateOrder], [TimeOrder], [UsernameEmp]) VALUES (N'HD7903', N'07/07/2021', N'22:38:50', N'davidbeck')
INSERT [dbo].[Order] ([IDOrder], [DateOrder], [TimeOrder], [UsernameEmp]) VALUES (N'HD7904', N'07/07/2021', N'23:38:31', N'davidbeck')
INSERT [dbo].[Order] ([IDOrder], [DateOrder], [TimeOrder], [UsernameEmp]) VALUES (N'HD7905', N'08/07/2021', N'08:34:04', N'davidbeck')
INSERT [dbo].[Order] ([IDOrder], [DateOrder], [TimeOrder], [UsernameEmp]) VALUES (N'HD7906', N'08/07/2021', N'15:57:34', N'davidbeck')
INSERT [dbo].[Order] ([IDOrder], [DateOrder], [TimeOrder], [UsernameEmp]) VALUES (N'HD7907', N'08/07/2021', N'15:58:02', N'davidbeck')
INSERT [dbo].[Order] ([IDOrder], [DateOrder], [TimeOrder], [UsernameEmp]) VALUES (N'HD7908', N'09/07/2021', N'11:58:37', N'davidbeck')
INSERT [dbo].[Order] ([IDOrder], [DateOrder], [TimeOrder], [UsernameEmp]) VALUES (N'HD7909', N'09/07/2021', N'12:05:55', N'davidbeck')
INSERT [dbo].[Order] ([IDOrder], [DateOrder], [TimeOrder], [UsernameEmp]) VALUES (N'HD7910', N'09/07/2021', N'12:14:44', N'davidbeck')
INSERT [dbo].[Order] ([IDOrder], [DateOrder], [TimeOrder], [UsernameEmp]) VALUES (N'HD7911', N'09/07/2021', N'12:16:39', N'davidbeck')
INSERT [dbo].[Order] ([IDOrder], [DateOrder], [TimeOrder], [UsernameEmp]) VALUES (N'HD7912', N'09/07/2021', N'12:34:18', N'davidbeck')
INSERT [dbo].[Order] ([IDOrder], [DateOrder], [TimeOrder], [UsernameEmp]) VALUES (N'HD7913', N'09/07/2021', N'12:36:16', N'davidbeck')
INSERT [dbo].[Order] ([IDOrder], [DateOrder], [TimeOrder], [UsernameEmp]) VALUES (N'HD7914', N'09/07/2021', N'12:44:33', N'davidbeck')
INSERT [dbo].[Order] ([IDOrder], [DateOrder], [TimeOrder], [UsernameEmp]) VALUES (N'HD7915', N'09/07/2021', N'13:52:23', N'davidbeck')
INSERT [dbo].[Order] ([IDOrder], [DateOrder], [TimeOrder], [UsernameEmp]) VALUES (N'HD7916', N'09/07/2021', N'16:47:53', N'davidbeck')
INSERT [dbo].[Order] ([IDOrder], [DateOrder], [TimeOrder], [UsernameEmp]) VALUES (N'HD7917', N'09/07/2021', N'16:49:17', N'davidbeck')
GO
INSERT [dbo].[OrderDetails] ([IDOrder], [IDProduct], [CusName], [Quantity], [NamePromo]) VALUES (N'HD0008', N'CE02', N'Khách vãng lai', 5, N'Không có')
INSERT [dbo].[OrderDetails] ([IDOrder], [IDProduct], [CusName], [Quantity], [NamePromo]) VALUES (N'HD0008', N'CF06', N'Khách vãng lai', 1, N'Không có')
INSERT [dbo].[OrderDetails] ([IDOrder], [IDProduct], [CusName], [Quantity], [NamePromo]) VALUES (N'HD1200', N'CF01', N'Khách vãng lai', 3, N'Không có')
INSERT [dbo].[OrderDetails] ([IDOrder], [IDProduct], [CusName], [Quantity], [NamePromo]) VALUES (N'HD1205', N'CF04', N'Khách vãng lai', 2, N'Không có')
INSERT [dbo].[OrderDetails] ([IDOrder], [IDProduct], [CusName], [Quantity], [NamePromo]) VALUES (N'HD1256', N'CF01', N'Khách vãng lai', 3, N'Không có')
INSERT [dbo].[OrderDetails] ([IDOrder], [IDProduct], [CusName], [Quantity], [NamePromo]) VALUES (N'HD1289', N'CF01', N'Khách vãng lai', 2, N'Không có')
INSERT [dbo].[OrderDetails] ([IDOrder], [IDProduct], [CusName], [Quantity], [NamePromo]) VALUES (N'HD1546', N'CF01', N'Khách vãng lai', 3, N'Không có')
INSERT [dbo].[OrderDetails] ([IDOrder], [IDProduct], [CusName], [Quantity], [NamePromo]) VALUES (N'HD4586', N'CF01', N'Khách vãng lai', 2, N'Không có')
INSERT [dbo].[OrderDetails] ([IDOrder], [IDProduct], [CusName], [Quantity], [NamePromo]) VALUES (N'HD5688', N'CF04', N'Khách vãng lai', 2, N'Không có')
INSERT [dbo].[OrderDetails] ([IDOrder], [IDProduct], [CusName], [Quantity], [NamePromo]) VALUES (N'HD5689', N'CF04', N'Khách vãng lai', 2, N'Không có')
INSERT [dbo].[OrderDetails] ([IDOrder], [IDProduct], [CusName], [Quantity], [NamePromo]) VALUES (N'HD7542', N'CF01', N'Khách vãng lai', 2, N'Không có')
INSERT [dbo].[OrderDetails] ([IDOrder], [IDProduct], [CusName], [Quantity], [NamePromo]) VALUES (N'HD7896', N'CF01', N'Khách vãng lai', 2, N'Không có')
INSERT [dbo].[OrderDetails] ([IDOrder], [IDProduct], [CusName], [Quantity], [NamePromo]) VALUES (N'HD7897', N'CF04', N'Khách vãng lai', 2, N'Không có')
INSERT [dbo].[OrderDetails] ([IDOrder], [IDProduct], [CusName], [Quantity], [NamePromo]) VALUES (N'HD7898', N'CF01', N'Khách vãng lai', 2, N'Không có')
INSERT [dbo].[OrderDetails] ([IDOrder], [IDProduct], [CusName], [Quantity], [NamePromo]) VALUES (N'HD7899', N'CF04', N'Khách vãng lai', 2, N'Không có')
INSERT [dbo].[OrderDetails] ([IDOrder], [IDProduct], [CusName], [Quantity], [NamePromo]) VALUES (N'HD7900', N'CF01', N'Khách vãng lai', 2, N'Không có')
INSERT [dbo].[OrderDetails] ([IDOrder], [IDProduct], [CusName], [Quantity], [NamePromo]) VALUES (N'HD7901', N'CF01', N'Khách vãng lai', 3, N'Không có')
INSERT [dbo].[OrderDetails] ([IDOrder], [IDProduct], [CusName], [Quantity], [NamePromo]) VALUES (N'HD7902', N'CF01', N'Khách vãng lai', 2, N'Không có')
INSERT [dbo].[OrderDetails] ([IDOrder], [IDProduct], [CusName], [Quantity], [NamePromo]) VALUES (N'HD7903', N'CF01', N'Khách vãng lai', 1, N'Không có')
INSERT [dbo].[OrderDetails] ([IDOrder], [IDProduct], [CusName], [Quantity], [NamePromo]) VALUES (N'HD7904', N'CF01', N'Khách vãng lai', 2, N'Không có')
INSERT [dbo].[OrderDetails] ([IDOrder], [IDProduct], [CusName], [Quantity], [NamePromo]) VALUES (N'HD7905', N'CF01', N'Khách vãng lai', 2, N'sdas')
INSERT [dbo].[OrderDetails] ([IDOrder], [IDProduct], [CusName], [Quantity], [NamePromo]) VALUES (N'HD7906', N'CF01', N'100000', 3, N'Khách hàng VIP')
INSERT [dbo].[OrderDetails] ([IDOrder], [IDProduct], [CusName], [Quantity], [NamePromo]) VALUES (N'HD7907', N'CF01', N'100000', 3, N'Khách hàng VIP')
INSERT [dbo].[OrderDetails] ([IDOrder], [IDProduct], [CusName], [Quantity], [NamePromo]) VALUES (N'HD7908', N'BA01', N'Khách vãng lai', 2, N'Không có')
INSERT [dbo].[OrderDetails] ([IDOrder], [IDProduct], [CusName], [Quantity], [NamePromo]) VALUES (N'HD7909', N'BA01', N'Khách vãng lai', 2, N'Không có')
INSERT [dbo].[OrderDetails] ([IDOrder], [IDProduct], [CusName], [Quantity], [NamePromo]) VALUES (N'HD7910', N'BA01', N'Khách vãng lai', 2, N'Không có')
INSERT [dbo].[OrderDetails] ([IDOrder], [IDProduct], [CusName], [Quantity], [NamePromo]) VALUES (N'HD7911', N'BA01', N'Khách vãng lai', 2, N'Không có')
INSERT [dbo].[OrderDetails] ([IDOrder], [IDProduct], [CusName], [Quantity], [NamePromo]) VALUES (N'HD7912', N'BA01', N'Khách vãng lai', 2, N'Không có')
INSERT [dbo].[OrderDetails] ([IDOrder], [IDProduct], [CusName], [Quantity], [NamePromo]) VALUES (N'HD7913', N'BA01', N'Khách vãng lai', 2, N'Không có')
INSERT [dbo].[OrderDetails] ([IDOrder], [IDProduct], [CusName], [Quantity], [NamePromo]) VALUES (N'HD7914', N'BA01', N'Khách vãng lai', 2, N'Không có')
INSERT [dbo].[OrderDetails] ([IDOrder], [IDProduct], [CusName], [Quantity], [NamePromo]) VALUES (N'HD7915', N'BA01', N'Khách vãng lai', 2, N'Không có')
INSERT [dbo].[OrderDetails] ([IDOrder], [IDProduct], [CusName], [Quantity], [NamePromo]) VALUES (N'HD7916', N'BA01', N'Khách vãng lai', 3, N'Không có')
INSERT [dbo].[OrderDetails] ([IDOrder], [IDProduct], [CusName], [Quantity], [NamePromo]) VALUES (N'HD7917', N'CF01', N'Khách vãng lai', 3, N'test5')
GO
INSERT [dbo].[Product] ([IDProduct], [ProductName], [IDType], [Price], [code], [acc]) VALUES (N'BA01', N'Bánh Snack', N'RE09', 100000, N'ME14', N'41')
INSERT [dbo].[Product] ([IDProduct], [ProductName], [IDType], [Price], [code], [acc]) VALUES (N'BA05', N'Xoài', N'T05', 10000, N'ME14', N'10')
INSERT [dbo].[Product] ([IDProduct], [ProductName], [IDType], [Price], [code], [acc]) VALUES (N'BA08', N'Xoài chuối', N'T05', 10000, N'ME14', N'17')
INSERT [dbo].[Product] ([IDProduct], [ProductName], [IDType], [Price], [code], [acc]) VALUES (N'BA32', N'Chuối1', N'T05', 124657, N'ME14', N'20')
INSERT [dbo].[Product] ([IDProduct], [ProductName], [IDType], [Price], [code], [acc]) VALUES (N'BL03', N'Mít', N'T12', 20000, N'ME14', N'30')
INSERT [dbo].[Product] ([IDProduct], [ProductName], [IDType], [Price], [code], [acc]) VALUES (N'CA01', N'CAPPUCCINO', N'T01', 42000, N'ME14', N'50')
INSERT [dbo].[Product] ([IDProduct], [ProductName], [IDType], [Price], [code], [acc]) VALUES (N'CA02', N'CAPPUCCINO', N'T02', 52000, N'ME14', N'80')
INSERT [dbo].[Product] ([IDProduct], [ProductName], [IDType], [Price], [code], [acc]) VALUES (N'CA03', N'CAPPUCCINO', N'T03', 62000, N'ME14', N'85')
INSERT [dbo].[Product] ([IDProduct], [ProductName], [IDType], [Price], [code], [acc]) VALUES (N'CE01', N'Cam ép', N'T04', 35000, N'ME14', N'23')
INSERT [dbo].[Product] ([IDProduct], [ProductName], [IDType], [Price], [code], [acc]) VALUES (N'CE02', N'Cam ép', N'T05', 40000, N'ME14', N'56')
INSERT [dbo].[Product] ([IDProduct], [ProductName], [IDType], [Price], [code], [acc]) VALUES (N'CF01', N'Cà phê đá', N'T01', 20000, N'ME14', N'84s')
INSERT [dbo].[Product] ([IDProduct], [ProductName], [IDType], [Price], [code], [acc]) VALUES (N'CF02', N'Cà phê đá', N'T02', 25000, N'ME14', N'84')
INSERT [dbo].[Product] ([IDProduct], [ProductName], [IDType], [Price], [code], [acc]) VALUES (N'CF03', N'Cà phê đá', N'T03', 30000, N'ME14', N'84')
INSERT [dbo].[Product] ([IDProduct], [ProductName], [IDType], [Price], [code], [acc]) VALUES (N'CF04', N'Cà phê sữa đá', N'T01', 25000, N'ME14', N'178')
INSERT [dbo].[Product] ([IDProduct], [ProductName], [IDType], [Price], [code], [acc]) VALUES (N'CF05', N'Cà phê sữa đá', N'T02', 30000, N'ME14', N'178')
INSERT [dbo].[Product] ([IDProduct], [ProductName], [IDType], [Price], [code], [acc]) VALUES (N'CF06', N'Cà phê sữa đá', N'T03', 35000, N'ME14', N'178')
INSERT [dbo].[Product] ([IDProduct], [ProductName], [IDType], [Price], [code], [acc]) VALUES (N'CP02', N'Cam', N'EW12', 10000, N'ME14', N'36')
INSERT [dbo].[Product] ([IDProduct], [ProductName], [IDType], [Price], [code], [acc]) VALUES (N'CP32', N'Cam', N'RL02', 20000, N'ME14', N'80')
INSERT [dbo].[Product] ([IDProduct], [ProductName], [IDType], [Price], [code], [acc]) VALUES (N'ES01', N'ESPRESSO MILK', N'T01', 30000, N'ME14', N'16')
INSERT [dbo].[Product] ([IDProduct], [ProductName], [IDType], [Price], [code], [acc]) VALUES (N'ES02', N'ESPRESSO MILK', N'T02', 40000, N'ME14', N'10')
INSERT [dbo].[Product] ([IDProduct], [ProductName], [IDType], [Price], [code], [acc]) VALUES (N'ES03', N'ESPRESSO MILK', N'T03', 50000, NULL, N'80')
INSERT [dbo].[Product] ([IDProduct], [ProductName], [IDType], [Price], [code], [acc]) VALUES (N'KE10', N'Kem cây', N'EW15', 12000, NULL, N'40')
INSERT [dbo].[Product] ([IDProduct], [ProductName], [IDType], [Price], [code], [acc]) VALUES (N'KE12', N'Kem cây', N'EW12', 12000, NULL, NULL)
INSERT [dbo].[Product] ([IDProduct], [ProductName], [IDType], [Price], [code], [acc]) VALUES (N'MN32', N'Ta', N'T03', 124354, NULL, NULL)
INSERT [dbo].[Product] ([IDProduct], [ProductName], [IDType], [Price], [code], [acc]) VALUES (N'ST01', N'Sinh tố thập cẩm', N'T04', 50000, NULL, NULL)
INSERT [dbo].[Product] ([IDProduct], [ProductName], [IDType], [Price], [code], [acc]) VALUES (N'ST02', N'Sinh tố thập cẩm', N'T05', 60000, NULL, NULL)
INSERT [dbo].[Product] ([IDProduct], [ProductName], [IDType], [Price], [code], [acc]) VALUES (N'TD01', N'Trà đào', N'T04', 40000, NULL, NULL)
INSERT [dbo].[Product] ([IDProduct], [ProductName], [IDType], [Price], [code], [acc]) VALUES (N'TD02', N'Trà đào', N'T05', 50000, NULL, NULL)
INSERT [dbo].[Product] ([IDProduct], [ProductName], [IDType], [Price], [code], [acc]) VALUES (N'TD03', N'Trà đào', N'T06', 60000, NULL, NULL)
GO
INSERT [dbo].[ProductType] ([IDType], [TypeName], [Size]) VALUES (N'EW12', N'aew', N'Thùng')
INSERT [dbo].[ProductType] ([IDType], [TypeName], [Size]) VALUES (N'EW15', N'Kem', N'Cái')
INSERT [dbo].[ProductType] ([IDType], [TypeName], [Size]) VALUES (N'LA45', N'gf', N'Hộp')
INSERT [dbo].[ProductType] ([IDType], [TypeName], [Size]) VALUES (N'ML14', N'ds', N'Hộp')
INSERT [dbo].[ProductType] ([IDType], [TypeName], [Size]) VALUES (N'MT12', N'ss', N'Thùng')
INSERT [dbo].[ProductType] ([IDType], [TypeName], [Size]) VALUES (N'RD12', N'Bánh', N'Lon')
INSERT [dbo].[ProductType] ([IDType], [TypeName], [Size]) VALUES (N'RE09', N'Rượu', N'Chai ')
INSERT [dbo].[ProductType] ([IDType], [TypeName], [Size]) VALUES (N'RL02', N'Bánh mì1', N'Gói')
INSERT [dbo].[ProductType] ([IDType], [TypeName], [Size]) VALUES (N'RO43', N'Táo', N'Lon')
INSERT [dbo].[ProductType] ([IDType], [TypeName], [Size]) VALUES (N'T01', N'Cà phê', N'Chai ')
INSERT [dbo].[ProductType] ([IDType], [TypeName], [Size]) VALUES (N'T02', N'Cà phê', N'Chai ')
INSERT [dbo].[ProductType] ([IDType], [TypeName], [Size]) VALUES (N'T03', N'Cà phê', N'Gói')
INSERT [dbo].[ProductType] ([IDType], [TypeName], [Size]) VALUES (N'T04', N'Nước trái cây', N'Gói')
INSERT [dbo].[ProductType] ([IDType], [TypeName], [Size]) VALUES (N'T05', N'Nước trái cây', N'Chai ')
INSERT [dbo].[ProductType] ([IDType], [TypeName], [Size]) VALUES (N'T06', N'Nước trái cây', N'Chai ')
INSERT [dbo].[ProductType] ([IDType], [TypeName], [Size]) VALUES (N'T12', N'Mì hảo hảo', N'Thùng')
GO
SET IDENTITY_INSERT [dbo].[Promotions] ON 

INSERT [dbo].[Promotions] ([IDPromo], [NamePromo], [DiscountPromo], [StartPromo], [EndPromo], [Description]) VALUES (2, N'test', 20, N'2021/06/14', N'2021/06/17', N'test')
INSERT [dbo].[Promotions] ([IDPromo], [NamePromo], [DiscountPromo], [StartPromo], [EndPromo], [Description]) VALUES (3, N'da', 15, N'2021/06/22', N'2021/06/26', N'sda')
INSERT [dbo].[Promotions] ([IDPromo], [NamePromo], [DiscountPromo], [StartPromo], [EndPromo], [Description]) VALUES (4, N'sdas', 25, N'2021/07/08', N'2021/07/15', N'asd')
INSERT [dbo].[Promotions] ([IDPromo], [NamePromo], [DiscountPromo], [StartPromo], [EndPromo], [Description]) VALUES (10, N'test1', 10, N'2021/07/08', N'2021/07/16', N'Khuyến mãi')
INSERT [dbo].[Promotions] ([IDPromo], [NamePromo], [DiscountPromo], [StartPromo], [EndPromo], [Description]) VALUES (11, N'test2', 10, N'2021/07/08', N'2021/07/16', N'Khuyến mãi cam')
INSERT [dbo].[Promotions] ([IDPromo], [NamePromo], [DiscountPromo], [StartPromo], [EndPromo], [Description]) VALUES (15, N'test5', 10, N'2021/07/09', N'2021/07/17', N'Khuyến mãi')
SET IDENTITY_INSERT [dbo].[Promotions] OFF
GO
SET IDENTITY_INSERT [dbo].[Revenue] ON 

INSERT [dbo].[Revenue] ([IDRevenue], [Date], [Money]) VALUES (10, N'23/06/2021', N'235000')
INSERT [dbo].[Revenue] ([IDRevenue], [Date], [Money]) VALUES (11, N'26/06/2021', N'60000')
INSERT [dbo].[Revenue] ([IDRevenue], [Date], [Money]) VALUES (12, N'01/07/2021', N'60000')
INSERT [dbo].[Revenue] ([IDRevenue], [Date], [Money]) VALUES (13, N'04/07/2021', N'330000')
INSERT [dbo].[Revenue] ([IDRevenue], [Date], [Money]) VALUES (14, N'05/07/2021', N'220000')
INSERT [dbo].[Revenue] ([IDRevenue], [Date], [Money]) VALUES (15, N'06/07/2021', N'60000')
INSERT [dbo].[Revenue] ([IDRevenue], [Date], [Money]) VALUES (16, N'07/07/2021', N'100000')
INSERT [dbo].[Revenue] ([IDRevenue], [Date], [Money]) VALUES (17, N'08/07/2021', N'138000')
INSERT [dbo].[Revenue] ([IDRevenue], [Date], [Money]) VALUES (18, N'09/07/2021', N'1954000')
SET IDENTITY_INSERT [dbo].[Revenue] OFF
GO
INSERT [dbo].[Store] ([code], [name], [phone], [email], [address]) VALUES (N'ME14', N'Chợ bà Chiểu', N'0123456789', N'bb@gmail.com', N'TpHCM')
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ__Customer__DA5B2F6D03D9F7FE]    Script Date: 7/12/2021 11:20:06 AM ******/
ALTER TABLE [dbo].[Customer] ADD UNIQUE NONCLUSTERED 
(
	[IdentityCard] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ__Promotio__F5ED10D979B5CC57]    Script Date: 7/12/2021 11:20:06 AM ******/
ALTER TABLE [dbo].[Promotions] ADD UNIQUE NONCLUSTERED 
(
	[NamePromo] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
ALTER TABLE [dbo].[Employee]  WITH CHECK ADD  CONSTRAINT [FK_Employee_Employee] FOREIGN KEY([UsernameEmp])
REFERENCES [dbo].[Employee] ([UsernameEmp])
GO
ALTER TABLE [dbo].[Employee] CHECK CONSTRAINT [FK_Employee_Employee]
GO
ALTER TABLE [dbo].[Order]  WITH CHECK ADD  CONSTRAINT [FK_Order_Employee] FOREIGN KEY([UsernameEmp])
REFERENCES [dbo].[Employee] ([UsernameEmp])
GO
ALTER TABLE [dbo].[Order] CHECK CONSTRAINT [FK_Order_Employee]
GO
ALTER TABLE [dbo].[OrderDetails]  WITH CHECK ADD FOREIGN KEY([IDProduct])
REFERENCES [dbo].[Product] ([IDProduct])
GO
ALTER TABLE [dbo].[OrderDetails]  WITH CHECK ADD FOREIGN KEY([IDProduct])
REFERENCES [dbo].[Product] ([IDProduct])
GO
ALTER TABLE [dbo].[OrderDetails]  WITH CHECK ADD  CONSTRAINT [FK_OrderDetails_Order] FOREIGN KEY([IDOrder])
REFERENCES [dbo].[Order] ([IDOrder])
GO
ALTER TABLE [dbo].[OrderDetails] CHECK CONSTRAINT [FK_OrderDetails_Order]
GO
ALTER TABLE [dbo].[OrderDetails]  WITH CHECK ADD  CONSTRAINT [FK_OrderDetails_OrderDetails] FOREIGN KEY([IDOrder], [IDProduct])
REFERENCES [dbo].[OrderDetails] ([IDOrder], [IDProduct])
GO
ALTER TABLE [dbo].[OrderDetails] CHECK CONSTRAINT [FK_OrderDetails_OrderDetails]
GO
ALTER TABLE [dbo].[Product]  WITH CHECK ADD FOREIGN KEY([IDType])
REFERENCES [dbo].[ProductType] ([IDType])
GO
ALTER TABLE [dbo].[Product]  WITH CHECK ADD FOREIGN KEY([IDType])
REFERENCES [dbo].[ProductType] ([IDType])
GO
ALTER TABLE [dbo].[Product]  WITH CHECK ADD  CONSTRAINT [FK_Product_Store] FOREIGN KEY([code])
REFERENCES [dbo].[Store] ([code])
GO
ALTER TABLE [dbo].[Product] CHECK CONSTRAINT [FK_Product_Store]
GO
ALTER TABLE [dbo].[Store]  WITH CHECK ADD  CONSTRAINT [FK_Store_Store] FOREIGN KEY([code])
REFERENCES [dbo].[Store] ([code])
GO
ALTER TABLE [dbo].[Store] CHECK CONSTRAINT [FK_Store_Store]
GO
USE [master]
GO
ALTER DATABASE [test4] SET  READ_WRITE 
GO
