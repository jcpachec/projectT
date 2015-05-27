/****** Object:  Table [dbo].[Configs]    Script Date: 5/26/2015 7:28:59 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Configs](
	[ConfigId] [int] IDENTITY(1,1) NOT NULL,
	[Class] [nvarchar](100) NOT NULL,
	[Attribute] [nvarchar](100) NOT NULL,
	[Value] [nvarchar](2000) NOT NULL,
	[Remarks] [nvarchar](250) NULL,
 CONSTRAINT [PK_Configs] PRIMARY KEY CLUSTERED 
(
	[ConfigId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET IDENTITY_INSERT [dbo].[Configs] ON 

GO
INSERT [dbo].[Configs] ([ConfigId], [Class], [Attribute], [Value], [Remarks]) VALUES (1, N'GlobalConfig', N'StartTime', N'16:00', N'Start time to show the Markers')
GO
INSERT [dbo].[Configs] ([ConfigId], [Class], [Attribute], [Value], [Remarks]) VALUES (2, N'GlobalConfig', N'EndDate', N'5:00', N'End time to end up showing the Markers, normally next day')
GO
INSERT [dbo].[Configs] ([ConfigId], [Class], [Attribute], [Value], [Remarks]) VALUES (5, N'GlobalConfig', N'Mode', N'NORMAL', N'Set this to DEBUG to show all markes, STOP to stop showing all markers ')
GO
SET IDENTITY_INSERT [dbo].[Configs] OFF
GO
