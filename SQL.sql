USE [appraisal]
GO

/****** Object:  Table [dbo].[t_user]    Script Date: 03/26/2018 13:31:52 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[t_admin](
	[ad_id] [int] IDENTITY(1,1) NOT NULL,
	[ad_name] [varchar](20) NULL,
	[ad_password] [varchar](50) NULL,
	[ad_class] [int] NULL,
 CONSTRAINT [PK_t_admin] PRIMARY KEY CLUSTERED 
(
	[ad_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

CREATE TABLE [dbo].[t_user](
	[u_id] [int] IDENTITY(1,1) NOT NULL,
	[oa_id] [varchar](50) NULL,
	[ding_id] [varchar](50) NULL,
	[u_nickname] [varchar](50) NULL,
	[u_short_name] [varchar](50) NULL,
	[u_username] [varchar](50) NOT NULL,
	[u_password] [varchar](50) NOT NULL,
	[u_email] [varchar](50) NULL,
	[u_status] [varchar](50) NULL,
	[u_role] [int] NULL,
	[u_department] [varchar](50) NULL,
	[token] [varchar](50) NULL,
	[datetime] [datetime] NULL,
 CONSTRAINT [PK_t_user] PRIMARY KEY CLUSTERED 
(
	[u_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

CREATE TABLE [dbo].[t_role](
	[r_id] [int] IDENTITY(1,1) NOT NULL,
	[r_name] [varchar](50) NULL,
	[r_weight] [int] NULL,
	[datetime] [datetime] NULL,
 CONSTRAINT [PK_t_role] PRIMARY KEY CLUSTERED 
(
	[r_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
CREATE TABLE [dbo].[t_project](
	[p_id] [int] IDENTITY(1,1) NOT NULL,
	[p_name] [varchar](50) NULL,
	[p_description] [varchar](50) NULL,
	[p_user_id] [int] NULL,
	[p_user_name] [varchar](50) NULL,
	[p_class] [int] NULL,
	[p_start] [datetime] NULL,
	[p_end] [datetime] NULL,
	[p_award] [decimal](18, 0) NULL,
	[datetime] [datetime] NULL,
 CONSTRAINT [PK_t_project] PRIMARY KEY CLUSTERED 
(
	[p_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

CREATE TABLE [dbo].[t_project_stage](
	[ps_id] [int] IDENTITY(1,1) NOT NULL,
	[ps_project_id] [int] NULL,
	[ps_stage] [int] NULL,
	[ps_stage_description] [varchar](50) NULL,
	[ps_start] [datetime] NULL,
	[ps_end] [datetime] NULL,
	[ps_award] [decimal](18, 0) NULL,
	[datetime] [datetime] NULL,
 CONSTRAINT [PK_t_project_stage] PRIMARY KEY CLUSTERED 
(
	[ps_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

CREATE TABLE [dbo].[t_orgnization](
	[o_id] [int] IDENTITY(1,1) NOT NULL,
	[o_project_id] [int] NULL,
	[o_project_name] [varchar](50) NULL,
	[o_user_id] [int] NULL,
	[o_user_name] [varchar](50) NULL,
	[o_role_id] [int] NULL,
	[datetime] [datetime] NULL,
 CONSTRAINT [PK_t_orgnization] PRIMARY KEY CLUSTERED 
(
	[o_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
CREATE TABLE [dbo].[t_affair](
	[a_id] [int] IDENTITY(1,1) NOT NULL,
	[a_affairs] [varchar](50) NULL,
	[a_sponser_id] [int] NULL,
	[a_project_id] [int] NULL,
	[a_start] [datetime] NULL,
	[a_end] [datetime] NULL,
	[datetime] [datetime] NULL,
 CONSTRAINT [PK_t_affair] PRIMARY KEY CLUSTERED 
(
	[a_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
CREATE TABLE [dbo].[t_judge](
	[j_id] [int] IDENTITY(1,1) NOT NULL,
	[j_affair_id] [int] NULL,
	[j_project_id] [int] NULL,
	[j_evaluator_id] [int] NULL,
	[j_evaluator_role_id] [int] NULL,
	[j_evaluated_id] [int] NULL,
	[j_atitude] [int] NULL,
	[j_quality_efficient] [int] NULL,
	[j_complishment] [int] NULL,
	[j_reason] [varchar](200) NULL,
	[datetime] [datetime] NULL,
 CONSTRAINT [PK_t_judge] PRIMARY KEY CLUSTERED 
(
	[j_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

CREATE TABLE [dbo].[t_infect](
	[i_id] [int] IDENTITY(1,1) NOT NULL,
	[i_affair_id] [int] NULL,
	[i_user_id] [int] NULL,
	[i_scored] [int] NULL,
	[datetime] [datetime] NULL,
 CONSTRAINT [PK_t_infect] PRIMARY KEY CLUSTERED 
(
	[i_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO


