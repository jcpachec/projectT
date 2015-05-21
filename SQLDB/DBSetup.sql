
CREATE DATABASE [TGeoData] 
GO

USE [TGeoData]
GO

/****** Object:  Table [dbo].[GeoData]    Script Date: 5/21/2015 6:26:13 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[GeoData](
	[recordID] [int] IDENTITY(1,1) NOT NULL,
	[description] [nvarchar](200) NOT NULL,
	[latitude] [float] NOT NULL,
	[longitude] [float] NOT NULL,
	[time] [datetime] NOT NULL,
	[remarks] [nvarchar](500) NOT NULL,
 CONSTRAINT [PK_GeoData] PRIMARY KEY CLUSTERED 
(
	[recordID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO


USE [TGeoData]
GO
SET IDENTITY_INSERT [dbo].[GeoData] ON 

GO
INSERT [dbo].[GeoData] ([recordID], [description], [latitude], [longitude], [time], [remarks]) VALUES (1, N'T1', 20.6583075, -103.4420149, CAST(0x00009FCB00000000 AS DateTime), N'Test1')
GO
INSERT [dbo].[GeoData] ([recordID], [description], [latitude], [longitude], [time], [remarks]) VALUES (2, N'T2', 20.6523392, -103.4413443, CAST(0x00009FCB00000000 AS DateTime), N'Test1')
GO
INSERT [dbo].[GeoData] ([recordID], [description], [latitude], [longitude], [time], [remarks]) VALUES (3, N'T3', 20.6489859, -103.4268701, CAST(0x00009FCB00000000 AS DateTime), N'Test1')
GO
INSERT [dbo].[GeoData] ([recordID], [description], [latitude], [longitude], [time], [remarks]) VALUES (4, N'T4', 20.6523392, -103.4413443, CAST(0x00009FCB00000000 AS DateTime), N'Test1')
GO
INSERT [dbo].[GeoData] ([recordID], [description], [latitude], [longitude], [time], [remarks]) VALUES (5, N'T5', 20.6489859, -103.4268701, CAST(0x0000A49E0181D884 AS DateTime), N'sample string 6')
GO
INSERT [dbo].[GeoData] ([recordID], [description], [latitude], [longitude], [time], [remarks]) VALUES (9, N'T6', 20.6433344, -103.4187271, CAST(0x0000A41300000000 AS DateTime), N'Test2')
GO
INSERT [dbo].[GeoData] ([recordID], [description], [latitude], [longitude], [time], [remarks]) VALUES (10, N'sample string 2', 3.1, 4.1, CAST(0x0000A49F011F945F AS DateTime), N'sample string 6')
GO
SET IDENTITY_INSERT [dbo].[GeoData] OFF
GO




