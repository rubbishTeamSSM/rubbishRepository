prompt PL/SQL Developer import file
prompt Created on 2016年12月14日 by neusoft
set feedback off
set define off
prompt Creating T_DSQGL_RWJBXX...
create table T_DSQGL_RWJBXX
(
  rw_dm    VARCHAR2(40) not null,
  rw_mc    VARCHAR2(100) not null,
  zxl_uuid VARCHAR2(40) not null,
  sjbds    VARCHAR2(1000) not null,
  xczx_sj  DATE not null,
  qy_bj    CHAR(1) not null,
  zx_bj    CHAR(1) not null,
  lr_sj    TIMESTAMP(6) not null,
  lrry_dm  VARCHAR2(40) not null
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on table T_DSQGL_RWJBXX
  is '定时任务基本信息';
comment on column T_DSQGL_RWJBXX.rw_dm
  is 'UUID';
comment on column T_DSQGL_RWJBXX.rw_mc
  is '任务名称';
comment on column T_DSQGL_RWJBXX.zxl_uuid
  is '执行类UUID';
comment on column T_DSQGL_RWJBXX.sjbds
  is '时间表达式（克隆表达式）';
comment on column T_DSQGL_RWJBXX.xczx_sj
  is '下次执行时间';
comment on column T_DSQGL_RWJBXX.qy_bj
  is '状态标记(默认0未启用，1启用，2作废)';
comment on column T_DSQGL_RWJBXX.zx_bj
  is '执行标记(默认0未执行，1执行中)';
comment on column T_DSQGL_RWJBXX.lr_sj
  is '录入时间';
comment on column T_DSQGL_RWJBXX.lrry_dm
  is '录入人员代码';
alter table T_DSQGL_RWJBXX
  add constraint PK_T_DSQGL_RWJBXX primary key (RW_MC)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt Creating T_DSQGL_RWYXJL...
create table T_DSQGL_RWYXJL
(
  uuid    VARCHAR2(40) not null,
  rw_dm   VARCHAR2(40) not null,
  zxks_sj DATE not null,
  zxjs_sj DATE not null,
  cg_bj   CHAR(1) not null,
  rzxx    VARCHAR2(2048),
  lr_sj   TIMESTAMP(6) not null,
  lrry_dm VARCHAR2(40) not null
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255;
comment on table T_DSQGL_RWYXJL
  is '定时任务运行记录';
comment on column T_DSQGL_RWYXJL.uuid
  is 'UUID';
comment on column T_DSQGL_RWYXJL.rw_dm
  is '任务代码';
comment on column T_DSQGL_RWYXJL.zxks_sj
  is '执行开始时间';
comment on column T_DSQGL_RWYXJL.zxjs_sj
  is '执行结束时间';
comment on column T_DSQGL_RWYXJL.cg_bj
  is '成功标记(默认0未成功，1成功)';
comment on column T_DSQGL_RWYXJL.rzxx
  is '日志信息（填写执行成功或者失败的信息）';
comment on column T_DSQGL_RWYXJL.lr_sj
  is '录入时间';
comment on column T_DSQGL_RWYXJL.lrry_dm
  is '录入人员代码';
alter table T_DSQGL_RWYXJL
  add constraint PK_T_DSQGL_RWYXJL primary key (UUID)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255;

prompt Creating T_DSQGL_RWZXLXX...
create table T_DSQGL_RWZXLXX
(
  uuid    VARCHAR2(40) not null,
  zxl_mc  VARCHAR2(100) not null,
  zxl     VARCHAR2(1000) not null,
  zf_bj   CHAR(1) not null,
  lr_sj   TIMESTAMP(6) not null,
  lrry_dm VARCHAR2(40) not null
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on table T_DSQGL_RWZXLXX
  is '定时任务执行类信息';
comment on column T_DSQGL_RWZXLXX.uuid
  is 'UUID';
comment on column T_DSQGL_RWZXLXX.zxl_mc
  is '执行类名称';
comment on column T_DSQGL_RWZXLXX.zxl
  is '执行类';
comment on column T_DSQGL_RWZXLXX.zf_bj
  is '作废标记(默认是0未作废，1作废)';
comment on column T_DSQGL_RWZXLXX.lr_sj
  is '录入时间';
comment on column T_DSQGL_RWZXLXX.lrry_dm
  is '录入人员代码';
alter table T_DSQGL_RWZXLXX
  add constraint PK_T_DSQGL_RWZXLXX primary key (ZXL_MC)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt Creating T_XT_BM...
create table T_XT_BM
(
  uuid      VARCHAR2(40) not null,
  bm_dm     VARCHAR2(40) not null,
  bm_mc     VARCHAR2(400) not null,
  bm_mc_j   VARCHAR2(400) not null,
  sjbm_dm   VARCHAR2(40),
  bmznfw_dm VARCHAR2(40),
  bmfzr_dm  VARCHAR2(40),
  zf_bj     CHAR(1) not null,
  bz        VARCHAR2(1024),
  lr_sj     TIMESTAMP(6) not null,
  lrry_dm   VARCHAR2(40) not null
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on table T_XT_BM
  is '部门';
comment on column T_XT_BM.uuid
  is 'UUID';
comment on column T_XT_BM.bm_dm
  is '部门代码';
comment on column T_XT_BM.bm_mc
  is '部门名称';
comment on column T_XT_BM.bm_mc_j
  is '部门名称简';
comment on column T_XT_BM.sjbm_dm
  is '上级部门代码';
comment on column T_XT_BM.bmznfw_dm
  is '部门职能范围代码';
comment on column T_XT_BM.bmfzr_dm
  is '部门负责人代码';
comment on column T_XT_BM.zf_bj
  is '作废标记';
comment on column T_XT_BM.bz
  is '备注';
comment on column T_XT_BM.lr_sj
  is '录入时间';
comment on column T_XT_BM.lrry_dm
  is '录入人员代码';
alter table T_XT_BM
  add constraint PK_T_XT_BM primary key (BM_DM)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt Creating T_XT_CD...
create table T_XT_CD
(
  uuid    VARCHAR2(40) not null,
  cd_dm   VARCHAR2(40) not null,
  cd_mc   VARCHAR2(400) not null,
  cd_url  VARCHAR2(400),
  fcd_dm  VARCHAR2(40),
  lx      CHAR(1) not null,
  cdcc    NUMBER,
  xspx    NUMBER not null,
  zf_bj   CHAR(1) not null,
  bz      VARCHAR2(1024),
  lr_sj   TIMESTAMP(6) not null,
  lrry_dm VARCHAR2(40) not null
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on table T_XT_CD
  is '菜单';
comment on column T_XT_CD.uuid
  is 'UUID';
comment on column T_XT_CD.cd_dm
  is '菜单代码';
comment on column T_XT_CD.cd_mc
  is '菜单名称';
comment on column T_XT_CD.cd_url
  is '菜单URL';
comment on column T_XT_CD.fcd_dm
  is '父菜代码';
comment on column T_XT_CD.lx
  is '类型(1代表菜单2代表目录)';
comment on column T_XT_CD.cdcc
  is '菜单层次（项目根节点是0，下面一次+1）';
comment on column T_XT_CD.xspx
  is '显示顺序';
comment on column T_XT_CD.zf_bj
  is '作废标记 1代表作废 0代表否';
comment on column T_XT_CD.bz
  is '备注';
comment on column T_XT_CD.lr_sj
  is '录入时间';
comment on column T_XT_CD.lrry_dm
  is '录入人员代码';
alter table T_XT_CD
  add constraint PK_T_XT_CD primary key (CD_DM)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt Creating T_XT_GW...
create table T_XT_GW
(
  uuid    VARCHAR2(40) not null,
  gw_dm   VARCHAR2(40) not null,
  gw_mc   VARCHAR2(400) not null,
  gw_mc_j VARCHAR2(400),
  bm_dm   VARCHAR2(40) not null,
  lr_sj   TIMESTAMP(6) not null,
  lrry_dm VARCHAR2(40) not null
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on table T_XT_GW
  is '岗位';
comment on column T_XT_GW.uuid
  is 'UUID';
comment on column T_XT_GW.gw_dm
  is '岗位代码';
comment on column T_XT_GW.gw_mc
  is '岗位名称';
comment on column T_XT_GW.gw_mc_j
  is '岗位名称简';
comment on column T_XT_GW.bm_dm
  is '部门代码';
comment on column T_XT_GW.lr_sj
  is '录入时间';
comment on column T_XT_GW.lrry_dm
  is '录入人员代码';
alter table T_XT_GW
  add constraint PK_T_XT_GW primary key (GW_DM, BM_DM)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt Creating T_XT_JS...
create table T_XT_JS
(
  uuid    VARCHAR2(40) not null,
  js_dm   VARCHAR2(40) not null,
  js_mc   VARCHAR2(400) not null,
  zf_bj   CHAR(1) not null,
  bz      VARCHAR2(1024),
  lr_sj   TIMESTAMP(6) not null,
  lrry_dm VARCHAR2(40) not null
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on table T_XT_JS
  is '角色';
comment on column T_XT_JS.uuid
  is 'UUID';
comment on column T_XT_JS.js_dm
  is '角色代码';
comment on column T_XT_JS.js_mc
  is '角色名称';
comment on column T_XT_JS.zf_bj
  is '作废标记 1代表作废 0代表否';
comment on column T_XT_JS.bz
  is '备注';
comment on column T_XT_JS.lr_sj
  is '录入时间';
comment on column T_XT_JS.lrry_dm
  is '录入人员代码';
alter table T_XT_JS
  add constraint PK_T_XT_JS primary key (JS_DM)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt Creating T_XT_JS_CD...
create table T_XT_JS_CD
(
  uuid    VARCHAR2(40) not null,
  js_dm   VARCHAR2(40) not null,
  cd_dm   VARCHAR2(40) not null,
  lr_sj   TIMESTAMP(6) not null,
  lrry_dm VARCHAR2(40) not null
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on table T_XT_JS_CD
  is '角色菜单';
comment on column T_XT_JS_CD.uuid
  is 'UUID';
comment on column T_XT_JS_CD.js_dm
  is '角色代码';
comment on column T_XT_JS_CD.cd_dm
  is '菜单代码';
comment on column T_XT_JS_CD.lr_sj
  is '录入时间';
comment on column T_XT_JS_CD.lrry_dm
  is '录入人员代码';
alter table T_XT_JS_CD
  add constraint PK_T_XT_JS_CD primary key (JS_DM, CD_DM)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt Creating T_XT_YH...
create table T_XT_YH
(
  uuid    VARCHAR2(40) not null,
  yh_dm   VARCHAR2(40) not null,
  yh_mc   VARCHAR2(40) not null,
  yh_mm   VARCHAR2(100) not null,
  gly_bj  CHAR(1) not null,
  sfzhm   VARCHAR2(18),
  xb      CHAR(1),
  gddh    VARCHAR2(40),
  sjhm    VARCHAR2(40),
  dzyj    VARCHAR2(40),
  macdz   VARCHAR2(40),
  ipdz    VARCHAR2(40),
  bm_dm   VARCHAR2(40) not null,
  xh      NUMBER,
  zf_bj   CHAR(1) not null,
  bz      VARCHAR2(1024),
  lr_sj   TIMESTAMP(6) not null,
  lrry_dm VARCHAR2(40) not null
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on table T_XT_YH
  is '用户';
comment on column T_XT_YH.uuid
  is 'UUID';
comment on column T_XT_YH.yh_dm
  is '用户代码';
comment on column T_XT_YH.yh_mc
  is '用户名称';
comment on column T_XT_YH.yh_mm
  is '用户密码';
comment on column T_XT_YH.gly_bj
  is '管理员标记 1代表是 0 代表否';
comment on column T_XT_YH.sfzhm
  is '身份证号码';
comment on column T_XT_YH.xb
  is '性别';
comment on column T_XT_YH.gddh
  is '固定电话';
comment on column T_XT_YH.sjhm
  is '手机号码';
comment on column T_XT_YH.dzyj
  is '电子邮件';
comment on column T_XT_YH.macdz
  is 'MAC地址';
comment on column T_XT_YH.ipdz
  is 'IP地址';
comment on column T_XT_YH.bm_dm
  is '部门代码';
comment on column T_XT_YH.xh
  is '序号';
comment on column T_XT_YH.zf_bj
  is '作废标记 1代表作废 0代表否';
comment on column T_XT_YH.bz
  is '备注';
comment on column T_XT_YH.lr_sj
  is '录入时间';
comment on column T_XT_YH.lrry_dm
  is '录入人员代码';
alter table T_XT_YH
  add constraint PK_T_XT_YH primary key (YH_DM)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt Creating T_XT_YH_BM...
create table T_XT_YH_BM
(
  uuid    VARCHAR2(40) not null,
  yh_dm   VARCHAR2(40) not null,
  bm_dm   VARCHAR2(40) not null,
  lr_sj   TIMESTAMP(6) not null,
  lrry_dm VARCHAR2(40) not null
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on table T_XT_YH_BM
  is '用户部门';
comment on column T_XT_YH_BM.uuid
  is 'UUID';
comment on column T_XT_YH_BM.yh_dm
  is '用户代码';
comment on column T_XT_YH_BM.bm_dm
  is '部门代码';
comment on column T_XT_YH_BM.lr_sj
  is '录入时间';
comment on column T_XT_YH_BM.lrry_dm
  is '录入人员代码';
alter table T_XT_YH_BM
  add constraint PK_T_XT_YH_BM primary key (YH_DM, BM_DM)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt Creating T_XT_YH_EWCD...
create table T_XT_YH_EWCD
(
  uuid    VARCHAR2(40) not null,
  yh_dm   VARCHAR2(40) not null,
  cd_dm   VARCHAR2(40) not null,
  yxq_q   TIMESTAMP(6) not null,
  yxq_z   TIMESTAMP(6) not null,
  lr_sj   TIMESTAMP(6) not null,
  lrry_dm VARCHAR2(40) not null
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on table T_XT_YH_EWCD
  is '用户额外菜单';
comment on column T_XT_YH_EWCD.uuid
  is 'UUID';
comment on column T_XT_YH_EWCD.yh_dm
  is '用户代码';
comment on column T_XT_YH_EWCD.cd_dm
  is '菜单代码';
comment on column T_XT_YH_EWCD.yxq_q
  is '有效期起';
comment on column T_XT_YH_EWCD.yxq_z
  is '有效期止';
comment on column T_XT_YH_EWCD.lr_sj
  is '录入时间';
comment on column T_XT_YH_EWCD.lrry_dm
  is '录入人员代码';
alter table T_XT_YH_EWCD
  add constraint PK_T_XT_YH_EWCD primary key (YH_DM, CD_DM)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt Creating T_XT_YH_GW...
create table T_XT_YH_GW
(
  uuid    VARCHAR2(40) not null,
  yh_dm   VARCHAR2(40) not null,
  gw_dm   VARCHAR2(40) not null,
  lr_sj   TIMESTAMP(6) not null,
  lrry_dm VARCHAR2(40) not null
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on table T_XT_YH_GW
  is '用户岗位';
comment on column T_XT_YH_GW.uuid
  is 'UUID';
comment on column T_XT_YH_GW.yh_dm
  is '用户代码';
comment on column T_XT_YH_GW.gw_dm
  is '岗位代码';
comment on column T_XT_YH_GW.lr_sj
  is '录入时间';
comment on column T_XT_YH_GW.lrry_dm
  is '录入人员代码';
alter table T_XT_YH_GW
  add constraint PK_T_XT_YH_GW primary key (YH_DM, GW_DM)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt Creating T_XT_YH_JS...
create table T_XT_YH_JS
(
  uuid    VARCHAR2(40) not null,
  yh_dm   VARCHAR2(40) not null,
  js_dm   VARCHAR2(40) not null,
  mrjs_bj CHAR(1),
  lr_sj   TIMESTAMP(6) not null,
  lrry_dm VARCHAR2(40) not null
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on table T_XT_YH_JS
  is '用户角色';
comment on column T_XT_YH_JS.uuid
  is 'UUID';
comment on column T_XT_YH_JS.yh_dm
  is '用户代码';
comment on column T_XT_YH_JS.js_dm
  is '角色代码';
comment on column T_XT_YH_JS.mrjs_bj
  is '默认角色标记';
comment on column T_XT_YH_JS.lr_sj
  is '录入时间';
comment on column T_XT_YH_JS.lrry_dm
  is '录入人员代码';
alter table T_XT_YH_JS
  add constraint PK_T_XT_YH_JS primary key (YH_DM, JS_DM)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt Disabling triggers for T_DSQGL_RWJBXX...
alter table T_DSQGL_RWJBXX disable all triggers;
prompt Disabling triggers for T_DSQGL_RWYXJL...
alter table T_DSQGL_RWYXJL disable all triggers;
prompt Disabling triggers for T_DSQGL_RWZXLXX...
alter table T_DSQGL_RWZXLXX disable all triggers;
prompt Disabling triggers for T_XT_BM...
alter table T_XT_BM disable all triggers;
prompt Disabling triggers for T_XT_CD...
alter table T_XT_CD disable all triggers;
prompt Disabling triggers for T_XT_GW...
alter table T_XT_GW disable all triggers;
prompt Disabling triggers for T_XT_JS...
alter table T_XT_JS disable all triggers;
prompt Disabling triggers for T_XT_JS_CD...
alter table T_XT_JS_CD disable all triggers;
prompt Disabling triggers for T_XT_YH...
alter table T_XT_YH disable all triggers;
prompt Disabling triggers for T_XT_YH_BM...
alter table T_XT_YH_BM disable all triggers;
prompt Disabling triggers for T_XT_YH_EWCD...
alter table T_XT_YH_EWCD disable all triggers;
prompt Disabling triggers for T_XT_YH_GW...
alter table T_XT_YH_GW disable all triggers;
prompt Disabling triggers for T_XT_YH_JS...
alter table T_XT_YH_JS disable all triggers;
prompt Loading T_DSQGL_RWJBXX...
insert into T_DSQGL_RWJBXX (rw_dm, rw_mc, zxl_uuid, sjbds, xczx_sj, qy_bj, zx_bj, lr_sj, lrry_dm)
values ('7316a0bb-76cc-42c6-8629-f4f73b86c2cf', '11', '22ABB489D19D5818E050FADCE1405AB6', '9 50 14 1/1 * ?', to_date('23-10-2015 14:50:09', 'dd-mm-yyyy hh24:mi:ss'), '0', '0', to_timestamp('22-10-2015 14:42:35.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'ef151505-d0c0-4437-9a0c-f0431028b2ab');
commit;
prompt 1 records loaded
prompt Loading T_DSQGL_RWYXJL...
prompt Table is empty
prompt Loading T_DSQGL_RWZXLXX...
insert into T_DSQGL_RWZXLXX (uuid, zxl_mc, zxl, zf_bj, lr_sj, lrry_dm)
values ('22ABB489D19D5818E050FADCE1405AB6', 'test', 'com.neusoft.sdd.businessConsole.user.action.UserAction', '0', to_timestamp('22-10-2015 14:35:47.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin');
commit;
prompt 1 records loaded
prompt Loading T_XT_BM...
insert into T_XT_BM (uuid, bm_dm, bm_mc, bm_mc_j, sjbm_dm, bmznfw_dm, bmfzr_dm, zf_bj, bz, lr_sj, lrry_dm)
values ('1', '1', '检察院', 'jcy', '0', null, null, '0', null, to_timestamp('19-10-2015 16:44:30.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin');
insert into T_XT_BM (uuid, bm_dm, bm_mc, bm_mc_j, sjbm_dm, bmznfw_dm, bmfzr_dm, zf_bj, bz, lr_sj, lrry_dm)
values ('2d117a6a-db81-4df7-9ac6-34b0b4a78e23', 'bm001', '组织', 'y', '1', null, null, '0', 'y', to_timestamp('19-10-2015 16:44:31.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'ef151505-d0c0-4437-9a0c-f0431028b2ab');
insert into T_XT_BM (uuid, bm_dm, bm_mc, bm_mc_j, sjbm_dm, bmznfw_dm, bmfzr_dm, zf_bj, bz, lr_sj, lrry_dm)
values ('4ca31886-6403-4b48-8c36-eb9a290145d3', 'bm007', '121', '121', 'bm006', null, null, '1', '121', to_timestamp('19-10-2015 16:44:31.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'ef151505-d0c0-4437-9a0c-f0431028b2ab');
insert into T_XT_BM (uuid, bm_dm, bm_mc, bm_mc_j, sjbm_dm, bmznfw_dm, bmfzr_dm, zf_bj, bz, lr_sj, lrry_dm)
values ('68ea582d-8eaa-48fc-b68a-17dbc5eeaf4b', 'bm9900', '组织21', 'bm99001', 'bm001', 'bm99001', null, '0', 'bm99001', to_timestamp('19-10-2015 16:44:31.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'ef151505-d0c0-4437-9a0c-f0431028b2ab');
insert into T_XT_BM (uuid, bm_dm, bm_mc, bm_mc_j, sjbm_dm, bmznfw_dm, bmfzr_dm, zf_bj, bz, lr_sj, lrry_dm)
values ('7373c399-d784-4683-b67b-ce380bd539a8', 'bm004', '组织1', '44', 'bm001', null, null, '0', '44', to_timestamp('19-10-2015 16:44:31.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'ef151505-d0c0-4437-9a0c-f0431028b2ab');
insert into T_XT_BM (uuid, bm_dm, bm_mc, bm_mc_j, sjbm_dm, bmznfw_dm, bmfzr_dm, zf_bj, bz, lr_sj, lrry_dm)
values ('a8490328-9b3e-4268-98f2-c0965806e495', 'bm005', '纪检1', '55', 'bm003', null, null, '0', '55', to_timestamp('19-10-2015 16:44:31.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'ef151505-d0c0-4437-9a0c-f0431028b2ab');
insert into T_XT_BM (uuid, bm_dm, bm_mc, bm_mc_j, sjbm_dm, bmznfw_dm, bmfzr_dm, zf_bj, bz, lr_sj, lrry_dm)
values ('ae73a787-87a9-4e80-9b0d-915ebb8eccbb', 'bm990045', '11', '1', '1', '111', null, '0', null, to_timestamp('19-10-2015 16:44:50.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'ef151505-d0c0-4437-9a0c-f0431028b2ab');
insert into T_XT_BM (uuid, bm_dm, bm_mc, bm_mc_j, sjbm_dm, bmznfw_dm, bmfzr_dm, zf_bj, bz, lr_sj, lrry_dm)
values ('c7f1ccaf-4247-49d6-88d9-eae09a4a649f', 'bm003', '纪检', 'w', '1', null, null, '0', 'w', to_timestamp('19-10-2015 16:44:50.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'ef151505-d0c0-4437-9a0c-f0431028b2ab');
insert into T_XT_BM (uuid, bm_dm, bm_mc, bm_mc_j, sjbm_dm, bmznfw_dm, bmfzr_dm, zf_bj, bz, lr_sj, lrry_dm)
values ('cada671f-22a9-475d-8db7-47f043edb75b', 'bm006', '纪检11', '78', 'bm005', null, null, '1', '787', to_timestamp('19-10-2015 16:44:50.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'ef151505-d0c0-4437-9a0c-f0431028b2ab');
insert into T_XT_BM (uuid, bm_dm, bm_mc, bm_mc_j, sjbm_dm, bmznfw_dm, bmfzr_dm, zf_bj, bz, lr_sj, lrry_dm)
values ('ee24adb3-5d03-4a8a-80d6-ac1182a83945', 'bm002', '档案', 'h', '1', null, null, '1', 'h', to_timestamp('19-10-2015 16:44:50.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'ef151505-d0c0-4437-9a0c-f0431028b2ab');
commit;
prompt 10 records loaded
prompt Loading T_XT_CD...
insert into T_XT_CD (uuid, cd_dm, cd_mc, cd_url, fcd_dm, lx, cdcc, xspx, zf_bj, bz, lr_sj, lrry_dm)
values ('e7a42812-9d95-4d24-b9c1-eaa05a16b875', 'd8cd6b8a-3c9c-4733-a8e2-b614bec69e5e', '定时任务', 'businessConsole/dsrw/dsrwList.do', '100100', '1', 2, 9, '0', '定时任务', to_timestamp('21-10-2015 15:18:04.808000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'ef151505-d0c0-4437-9a0c-f0431028b2ab');
insert into T_XT_CD (uuid, cd_dm, cd_mc, cd_url, fcd_dm, lx, cdcc, xspx, zf_bj, bz, lr_sj, lrry_dm)
values ('1', '100', '系统菜单', null, '0', '2', 0, 1, '0', null, to_timestamp('19-10-2015 16:45:54.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin');
insert into T_XT_CD (uuid, cd_dm, cd_mc, cd_url, fcd_dm, lx, cdcc, xspx, zf_bj, bz, lr_sj, lrry_dm)
values ('2', '100100', '系统管理', null, '100', '2', 1, 6, '0', null, to_timestamp('19-10-2015 16:45:54.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin');
insert into T_XT_CD (uuid, cd_dm, cd_mc, cd_url, fcd_dm, lx, cdcc, xspx, zf_bj, bz, lr_sj, lrry_dm)
values ('3', '100100100', '菜单管理', 'businessConsole/menu/systemMenuManager.do', '100100', '1', 2, 1, '0', null, to_timestamp('19-10-2015 16:46:13.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin');
insert into T_XT_CD (uuid, cd_dm, cd_mc, cd_url, fcd_dm, lx, cdcc, xspx, zf_bj, bz, lr_sj, lrry_dm)
values ('5043dc36-c45a-4202-b587-54767819e249', 'fc0e7244-363e-445e-a128-e49283f8b08f', '岗位管理', 'businessConsole/station/systemStationManager.do', '100100', '1', 2, 8, '0', '岗位管理', to_timestamp('19-10-2015 16:48:34.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'ef151505-d0c0-4437-9a0c-f0431028b2ab');
insert into T_XT_CD (uuid, cd_dm, cd_mc, cd_url, fcd_dm, lx, cdcc, xspx, zf_bj, bz, lr_sj, lrry_dm)
values ('751a667f-752c-434d-8c58-e21b55417615', 'deb9822a-0a01-4b90-ab42-15237297a776', '用户管理', 'businessConsole/user/systemUserManager.do', '100100', '1', 2, 2, '0', '用户管理', to_timestamp('19-10-2015 16:48:34.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'ef151505-d0c0-4437-9a0c-f0431028b2ab');
insert into T_XT_CD (uuid, cd_dm, cd_mc, cd_url, fcd_dm, lx, cdcc, xspx, zf_bj, bz, lr_sj, lrry_dm)
values ('8e2c3633-1051-4fc8-b058-64bf456c51da', '91f4f73a-3f69-4354-8fbe-281eb717ddcb', '角色管理', 'businessConsole/role/systemRoleManager.do', '100100', '1', 2, 3, '0', '角色管理', to_timestamp('19-10-2015 16:48:34.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'ef151505-d0c0-4437-9a0c-f0431028b2ab');
insert into T_XT_CD (uuid, cd_dm, cd_mc, cd_url, fcd_dm, lx, cdcc, xspx, zf_bj, bz, lr_sj, lrry_dm)
values ('a1b3e898-b0c2-435b-9967-47e15b4d8597', 'ab9c0af3-fd4e-43f9-915c-7c0287aedace', '部门管理', 'businessConsole/department/systemDepartmentManager.do', '100100', '1', 2, 7, '0', '部门管理', to_timestamp('19-10-2015 16:48:34.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'ef151505-d0c0-4437-9a0c-f0431028b2ab');
commit;
prompt 8 records loaded
prompt Loading T_XT_GW...
insert into T_XT_GW (uuid, gw_dm, gw_mc, gw_mc_j, bm_dm, lr_sj, lrry_dm)
values ('efb6346e-828c-4919-903f-0b69a6c6c823', 'cs', '测试', '测试', 'bm9900', to_timestamp('20-10-2015 17:56:51.376000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'ef151505-d0c0-4437-9a0c-f0431028b2ab');
commit;
prompt 1 records loaded
prompt Loading T_XT_JS...
insert into T_XT_JS (uuid, js_dm, js_mc, zf_bj, bz, lr_sj, lrry_dm)
values ('1', '1', '管理员', '0', '管理员', to_timestamp('19-10-2015 16:50:33.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin');
insert into T_XT_JS (uuid, js_dm, js_mc, zf_bj, bz, lr_sj, lrry_dm)
values ('52c18383-5b46-4f60-97ef-bd6afaf0bf20', 'b7e5853f-dab6-4375-bdba-cde3b8d6c832', '普通用户', '0', '普通用户', to_timestamp('19-10-2015 16:50:33.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'ef151505-d0c0-4437-9a0c-f0431028b2ab');
insert into T_XT_JS (uuid, js_dm, js_mc, zf_bj, bz, lr_sj, lrry_dm)
values ('f42757ba-78f4-4130-8469-5ca095352d38', 'ac9abe6a-0713-44c1-b910-90b71f22795c', '等等', '1', '等等', to_timestamp('17-11-2014 15:48:31.254000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'ef151505-d0c0-4437-9a0c-f0431028b2ab');
commit;
prompt 3 records loaded
prompt Loading T_XT_JS_CD...
insert into T_XT_JS_CD (uuid, js_dm, cd_dm, lr_sj, lrry_dm)
values ('229928E44D562429E050FADCE140428F', '1', '100', to_timestamp('21-10-2015 15:16:39.977264', 'dd-mm-yyyy hh24:mi:ss.ff'), 'ef151505-d0c0-4437-9a0c-f0431028b2ab');
insert into T_XT_JS_CD (uuid, js_dm, cd_dm, lr_sj, lrry_dm)
values ('229928E44D572429E050FADCE140428F', '1', '100100', to_timestamp('21-10-2015 15:16:39.977264', 'dd-mm-yyyy hh24:mi:ss.ff'), 'ef151505-d0c0-4437-9a0c-f0431028b2ab');
insert into T_XT_JS_CD (uuid, js_dm, cd_dm, lr_sj, lrry_dm)
values ('229928E44D582429E050FADCE140428F', '1', '100100100', to_timestamp('21-10-2015 15:16:39.977264', 'dd-mm-yyyy hh24:mi:ss.ff'), 'ef151505-d0c0-4437-9a0c-f0431028b2ab');
insert into T_XT_JS_CD (uuid, js_dm, cd_dm, lr_sj, lrry_dm)
values ('229928E44D592429E050FADCE140428F', '1', 'deb9822a-0a01-4b90-ab42-15237297a776', to_timestamp('21-10-2015 15:16:39.977264', 'dd-mm-yyyy hh24:mi:ss.ff'), 'ef151505-d0c0-4437-9a0c-f0431028b2ab');
insert into T_XT_JS_CD (uuid, js_dm, cd_dm, lr_sj, lrry_dm)
values ('229928E44D5A2429E050FADCE140428F', '1', '91f4f73a-3f69-4354-8fbe-281eb717ddcb', to_timestamp('21-10-2015 15:16:39.977264', 'dd-mm-yyyy hh24:mi:ss.ff'), 'ef151505-d0c0-4437-9a0c-f0431028b2ab');
insert into T_XT_JS_CD (uuid, js_dm, cd_dm, lr_sj, lrry_dm)
values ('229928E44D5B2429E050FADCE140428F', '1', 'ab9c0af3-fd4e-43f9-915c-7c0287aedace', to_timestamp('21-10-2015 15:16:39.977264', 'dd-mm-yyyy hh24:mi:ss.ff'), 'ef151505-d0c0-4437-9a0c-f0431028b2ab');
insert into T_XT_JS_CD (uuid, js_dm, cd_dm, lr_sj, lrry_dm)
values ('229928E44D5C2429E050FADCE140428F', '1', 'fc0e7244-363e-445e-a128-e49283f8b08f', to_timestamp('21-10-2015 15:16:39.977264', 'dd-mm-yyyy hh24:mi:ss.ff'), 'ef151505-d0c0-4437-9a0c-f0431028b2ab');
insert into T_XT_JS_CD (uuid, js_dm, cd_dm, lr_sj, lrry_dm)
values ('229928E44D5D2429E050FADCE140428F', '1', 'd8cd6b8a-3c9c-4733-a8e2-b614bec69e5e', to_timestamp('21-10-2015 15:16:39.977264', 'dd-mm-yyyy hh24:mi:ss.ff'), 'ef151505-d0c0-4437-9a0c-f0431028b2ab');
insert into T_XT_JS_CD (uuid, js_dm, cd_dm, lr_sj, lrry_dm)
values ('00b50f5f-3a97-4f0d-a751-45cde2bedbc6', 'b7e5853f-dab6-4375-bdba-cde3b8d6c832', '100100100', to_timestamp('14-11-2014 15:07:11.505000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'ef151505-d0c0-4437-9a0c-f0431028b2ab');
insert into T_XT_JS_CD (uuid, js_dm, cd_dm, lr_sj, lrry_dm)
values ('7ed2bc4d-9603-4568-a2d5-631aaabad973', 'b7e5853f-dab6-4375-bdba-cde3b8d6c832', 'deb9822a-0a01-4b90-ab42-15237297a776', to_timestamp('14-11-2014 15:07:11.505000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'ef151505-d0c0-4437-9a0c-f0431028b2ab');
insert into T_XT_JS_CD (uuid, js_dm, cd_dm, lr_sj, lrry_dm)
values ('fb3c47b8-c365-4af9-baa3-1d3815184365', 'b7e5853f-dab6-4375-bdba-cde3b8d6c832', '91f4f73a-3f69-4354-8fbe-281eb717ddcb', to_timestamp('14-11-2014 15:07:11.505000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'ef151505-d0c0-4437-9a0c-f0431028b2ab');
insert into T_XT_JS_CD (uuid, js_dm, cd_dm, lr_sj, lrry_dm)
values ('8f5c62e8-8b4a-4a6e-816c-ff11298938d5', 'b7e5853f-dab6-4375-bdba-cde3b8d6c832', '100', to_timestamp('14-11-2014 15:07:11.505000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'ef151505-d0c0-4437-9a0c-f0431028b2ab');
insert into T_XT_JS_CD (uuid, js_dm, cd_dm, lr_sj, lrry_dm)
values ('dd5438f8-01b1-4888-b0e1-c97310be5ea3', 'b7e5853f-dab6-4375-bdba-cde3b8d6c832', '100100', to_timestamp('14-11-2014 15:07:11.505000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'ef151505-d0c0-4437-9a0c-f0431028b2ab');
commit;
prompt 13 records loaded
prompt Loading T_XT_YH...
insert into T_XT_YH (uuid, yh_dm, yh_mc, yh_mm, gly_bj, sfzhm, xb, gddh, sjhm, dzyj, macdz, ipdz, bm_dm, xh, zf_bj, bz, lr_sj, lrry_dm)
values ('06385025-9722-40cb-9e7d-9ce32b6567c4', 'laihua1', 'laihua2', '8D969EEF6ECAD3C29A3A629280E686CF0C3F5D5A86AFF3CA12020C923ADC6C92', '0', '321', '0', '2312', '23212', '2312', null, null, 'bm004', 112, '1', '3212', to_timestamp('19-10-2015 17:03:16.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'ef151505-d0c0-4437-9a0c-f0431028b2ab');
insert into T_XT_YH (uuid, yh_dm, yh_mc, yh_mm, gly_bj, sfzhm, xb, gddh, sjhm, dzyj, macdz, ipdz, bm_dm, xh, zf_bj, bz, lr_sj, lrry_dm)
values ('0f49445d-98c6-43a8-bf85-8af3e206711b', 'cc001', 'cc001', '8D969EEF6ECAD3C29A3A629280E686CF0C3F5D5A86AFF3CA12020C923ADC6C92', '0', '1', '0', '1', '1', 'c', null, null, 'bm004', 0, '0', '3', to_timestamp('19-10-2015 17:03:16.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'ef151505-d0c0-4437-9a0c-f0431028b2ab');
insert into T_XT_YH (uuid, yh_dm, yh_mc, yh_mm, gly_bj, sfzhm, xb, gddh, sjhm, dzyj, macdz, ipdz, bm_dm, xh, zf_bj, bz, lr_sj, lrry_dm)
values ('176c4d11-7eff-4e92-8111-11d20c9b1cc9', 'ef151505-d0c0-4437-9a0c-f0431028b2ab', 'admin', '8D969EEF6ECAD3C29A3A629280E686CF0C3F5D5A86AFF3CA12020C923ADC6C92', '1', '321321', '1', '1895146', '111222', 'q@qq.com', 'dssds', '12', '201001', 1, '0', '管理员', to_timestamp('19-10-2015 17:03:16.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin');
insert into T_XT_YH (uuid, yh_dm, yh_mc, yh_mm, gly_bj, sfzhm, xb, gddh, sjhm, dzyj, macdz, ipdz, bm_dm, xh, zf_bj, bz, lr_sj, lrry_dm)
values ('8dd2922c-84f1-4bf6-b520-500e97c8cafc', 'ae394692-ae03-4967-8713-573d376993b5', 'gh', '8D969EEF6ECAD3C29A3A629280E686CF0C3F5D5A86AFF3CA12020C923ADC6C92', '0', '55', '1', '55', '1', '55@qq.com', null, null, 'bm004', 1, '1', '1', to_timestamp('19-10-2015 17:03:16.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'ef151505-d0c0-4437-9a0c-f0431028b2ab');
insert into T_XT_YH (uuid, yh_dm, yh_mc, yh_mm, gly_bj, sfzhm, xb, gddh, sjhm, dzyj, macdz, ipdz, bm_dm, xh, zf_bj, bz, lr_sj, lrry_dm)
values ('a27f2ded-c7f7-45c6-a640-39d3bc7a844f', '81c24fc1-a41d-43be-a6da-a2f4452492ce', '121212', '8D969EEF6ECAD3C29A3A629280E686CF0C3F5D5A86AFF3CA12020C923ADC6C92', '0', null, '0', null, null, null, null, null, 'bm004', 1, '1', null, to_timestamp('19-10-2015 17:03:16.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'ef151505-d0c0-4437-9a0c-f0431028b2ab');
insert into T_XT_YH (uuid, yh_dm, yh_mc, yh_mm, gly_bj, sfzhm, xb, gddh, sjhm, dzyj, macdz, ipdz, bm_dm, xh, zf_bj, bz, lr_sj, lrry_dm)
values ('a9814003-1ebb-4ddb-81fb-553f90b1f675', 'cc0012', 'cc0012', '8D969EEF6ECAD3C29A3A629280E686CF0C3F5D5A86AFF3CA12020C923ADC6C92', '0', '1', '0', '1', '1', '1', '12', '12', 'bm001', 1, '0', '1', to_timestamp('19-10-2015 17:03:16.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'ef151505-d0c0-4437-9a0c-f0431028b2ab');
insert into T_XT_YH (uuid, yh_dm, yh_mc, yh_mm, gly_bj, sfzhm, xb, gddh, sjhm, dzyj, macdz, ipdz, bm_dm, xh, zf_bj, bz, lr_sj, lrry_dm)
values ('ac3a422c-cf27-402e-83fa-f6f21914ec7f', '300300', 'yunsan', '8D969EEF6ECAD3C29A3A629280E686CF0C3F5D5A86AFF3CA12020C923ADC6C92', '0', '321', '1', '321', '321', '312@qq.com', null, null, 'bm004', 1, '0', '1111', to_timestamp('19-10-2015 17:03:16.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'ef151505-d0c0-4437-9a0c-f0431028b2ab');
insert into T_XT_YH (uuid, yh_dm, yh_mc, yh_mm, gly_bj, sfzhm, xb, gddh, sjhm, dzyj, macdz, ipdz, bm_dm, xh, zf_bj, bz, lr_sj, lrry_dm)
values ('ba250285-41f1-412f-b8fe-19efcfa03627', '284f067c-e934-4395-b794-8c9dc9cf03fe', 'yuy', '8D969EEF6ECAD3C29A3A629280E686CF0C3F5D5A86AFF3CA12020C923ADC6C92', '0', '12', '0', '12', '12', '12', null, null, 'bm004', 1, '0', '12', to_timestamp('19-10-2015 17:03:16.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'ef151505-d0c0-4437-9a0c-f0431028b2ab');
insert into T_XT_YH (uuid, yh_dm, yh_mc, yh_mm, gly_bj, sfzhm, xb, gddh, sjhm, dzyj, macdz, ipdz, bm_dm, xh, zf_bj, bz, lr_sj, lrry_dm)
values ('bc34db73-fc83-4c5d-b4be-97f07f9a3b3f', '323232', '33', '8D969EEF6ECAD3C29A3A629280E686CF0C3F5D5A86AFF3CA12020C923ADC6C92', '0', '33', '0', '33', '33', '33', null, null, 'bm007', 1, '1', '33', to_timestamp('19-10-2015 17:03:16.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'ef151505-d0c0-4437-9a0c-f0431028b2ab');
insert into T_XT_YH (uuid, yh_dm, yh_mc, yh_mm, gly_bj, sfzhm, xb, gddh, sjhm, dzyj, macdz, ipdz, bm_dm, xh, zf_bj, bz, lr_sj, lrry_dm)
values ('ef53c60b-194f-4b29-b67b-80f942131cc0', '08aa6fc7-7237-4680-b8b7-139dc6e5ceba', 'test', '8D969EEF6ECAD3C29A3A629280E686CF0C3F5D5A86AFF3CA12020C923ADC6C92', '0', '312', '0', '133', '122', '133@126.com', null, null, 'bm004', 1, '1', '121', to_timestamp('19-10-2015 17:03:16.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'ef151505-d0c0-4437-9a0c-f0431028b2ab');
insert into T_XT_YH (uuid, yh_dm, yh_mc, yh_mm, gly_bj, sfzhm, xb, gddh, sjhm, dzyj, macdz, ipdz, bm_dm, xh, zf_bj, bz, lr_sj, lrry_dm)
values ('c8884940-23b6-4b6b-9ab3-a2ccd0ae0f04', '4334', '4334', '8D969EEF6ECAD3C29A3A629280E686CF0C3F5D5A86AFF3CA12020C923ADC6C92', '0', '4334', '0', '4334', '4334', '1', '4334', '1', 'bm9900', 32, '1', '1', to_timestamp('17-11-2014 16:07:07.077000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'ef151505-d0c0-4437-9a0c-f0431028b2ab');
insert into T_XT_YH (uuid, yh_dm, yh_mc, yh_mm, gly_bj, sfzhm, xb, gddh, sjhm, dzyj, macdz, ipdz, bm_dm, xh, zf_bj, bz, lr_sj, lrry_dm)
values ('4444eb90-ca47-4dfa-9196-11e47c862326', '56', '56', '8D969EEF6ECAD3C29A3A629280E686CF0C3F5D5A86AFF3CA12020C923ADC6C92', '0', '56', '0', null, null, null, null, null, 'bm001', 561, '1', null, to_timestamp('17-11-2014 16:15:19.522000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'ef151505-d0c0-4437-9a0c-f0431028b2ab');
insert into T_XT_YH (uuid, yh_dm, yh_mc, yh_mm, gly_bj, sfzhm, xb, gddh, sjhm, dzyj, macdz, ipdz, bm_dm, xh, zf_bj, bz, lr_sj, lrry_dm)
values ('f48b370a-2a60-4c35-8b26-652ee9fbc79b', '67', '67', '8D969EEF6ECAD3C29A3A629280E686CF0C3F5D5A86AFF3CA12020C923ADC6C92', '0', null, '0', null, null, null, null, null, 'bm001', 67, '1', null, to_timestamp('17-11-2014 16:19:15.982000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'ef151505-d0c0-4437-9a0c-f0431028b2ab');
insert into T_XT_YH (uuid, yh_dm, yh_mc, yh_mm, gly_bj, sfzhm, xb, gddh, sjhm, dzyj, macdz, ipdz, bm_dm, xh, zf_bj, bz, lr_sj, lrry_dm)
values ('ebcdf7fc-de70-41c8-88ec-1fe024ef2b08', '89', '98', '8D969EEF6ECAD3C29A3A629280E686CF0C3F5D5A86AFF3CA12020C923ADC6C92', '0', null, '0', null, null, null, null, null, 'bm004', 981, '1', null, to_timestamp('17-11-2014 16:35:27.745000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'ef151505-d0c0-4437-9a0c-f0431028b2ab');
commit;
prompt 14 records loaded
prompt Loading T_XT_YH_BM...
insert into T_XT_YH_BM (uuid, yh_dm, bm_dm, lr_sj, lrry_dm)
values ('7baa070d-3a3d-11e4-a55d-18037385bc02', 'cc0012', 'bm001', to_timestamp('19-10-2015 17:03:57.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'ef151505-d0c0-4437-9a0c-f0431028b2ab');
insert into T_XT_YH_BM (uuid, yh_dm, bm_dm, lr_sj, lrry_dm)
values ('e595fee6-3999-11e4-b310-18037385bc02', 'cc001', 'bm004', to_timestamp('19-10-2015 17:03:58.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'ef151505-d0c0-4437-9a0c-f0431028b2ab');
insert into T_XT_YH_BM (uuid, yh_dm, bm_dm, lr_sj, lrry_dm)
values ('e597600d-3999-11e4-b310-18037385bc02', 'cc001', 'bm003', to_timestamp('19-10-2015 17:03:58.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'ef151505-d0c0-4437-9a0c-f0431028b2ab');
insert into T_XT_YH_BM (uuid, yh_dm, bm_dm, lr_sj, lrry_dm)
values ('e597613c-3999-11e4-b310-18037385bc02', 'cc001', 'bm005', to_timestamp('19-10-2015 17:03:58.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'ef151505-d0c0-4437-9a0c-f0431028b2ab');
commit;
prompt 4 records loaded
prompt Loading T_XT_YH_EWCD...
insert into T_XT_YH_EWCD (uuid, yh_dm, cd_dm, yxq_q, yxq_z, lr_sj, lrry_dm)
values ('a9020ee2-340c-11e4-a056-082e5f19f7d7', 'ef151505-d0c0-4437-9a0c-f0431028b2ab', '100100', to_timestamp('14-09-2014 00:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), to_timestamp('24-11-2014 00:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), to_timestamp('14-09-2014 00:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'ef151505-d0c0-4437-9a0c-f0431028b2ab');
insert into T_XT_YH_EWCD (uuid, yh_dm, cd_dm, yxq_q, yxq_z, lr_sj, lrry_dm)
values ('a9021a41-340c-11e4-a056-082e5f19f7d7', 'ef151505-d0c0-4437-9a0c-f0431028b2ab', '100100100', to_timestamp('14-09-2014 00:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), to_timestamp('24-11-2014 00:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), to_timestamp('14-09-2014 00:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'ef151505-d0c0-4437-9a0c-f0431028b2ab');
insert into T_XT_YH_EWCD (uuid, yh_dm, cd_dm, yxq_q, yxq_z, lr_sj, lrry_dm)
values ('a9021ad3-340c-11e4-a056-082e5f19f7d7', 'ef151505-d0c0-4437-9a0c-f0431028b2ab', 'deb9822a-0a01-4b90-ab42-15237297a776', to_timestamp('14-09-2014 00:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), to_timestamp('24-11-2014 00:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), to_timestamp('14-09-2014 00:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'ef151505-d0c0-4437-9a0c-f0431028b2ab');
insert into T_XT_YH_EWCD (uuid, yh_dm, cd_dm, yxq_q, yxq_z, lr_sj, lrry_dm)
values ('a9021b46-340c-11e4-a056-082e5f19f7d7', 'ef151505-d0c0-4437-9a0c-f0431028b2ab', '91f4f73a-3f69-4354-8fbe-281eb717ddcb', to_timestamp('14-09-2014 00:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), to_timestamp('24-11-2014 00:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), to_timestamp('14-09-2014 00:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'ef151505-d0c0-4437-9a0c-f0431028b2ab');
insert into T_XT_YH_EWCD (uuid, yh_dm, cd_dm, yxq_q, yxq_z, lr_sj, lrry_dm)
values ('a9021bfd-340c-11e4-a056-082e5f19f7d7', 'ef151505-d0c0-4437-9a0c-f0431028b2ab', '100', to_timestamp('14-09-2014 00:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), to_timestamp('24-11-2014 00:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), to_timestamp('14-09-2014 00:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'ef151505-d0c0-4437-9a0c-f0431028b2ab');
commit;
prompt 5 records loaded
prompt Loading T_XT_YH_GW...
insert into T_XT_YH_GW (uuid, yh_dm, gw_dm, lr_sj, lrry_dm)
values ('cec3fefc-3c01-11e4-b3fe-18037385bc02', 'cc001', 'gw123', to_timestamp('19-10-2015 17:04:20.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'ef151505-d0c0-4437-9a0c-f0431028b2ab');
commit;
prompt 1 records loaded
prompt Loading T_XT_YH_JS...
insert into T_XT_YH_JS (uuid, yh_dm, js_dm, mrjs_bj, lr_sj, lrry_dm)
values ('250781f2-3a38-11e4-a55d-18037385bc02', 'cc001', 'b7e5853f-dab6-4375-bdba-cde3b8d6c832', '0', to_timestamp('19-10-2015 17:04:39.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'ef151505-d0c0-4437-9a0c-f0431028b2ab');
insert into T_XT_YH_JS (uuid, yh_dm, js_dm, mrjs_bj, lr_sj, lrry_dm)
values ('66eb07fe-37fd-11e4-8f0a-082e5f19f7d7', 'ef151505-d0c0-4437-9a0c-f0431028b2ab', '1', '0', to_timestamp('19-10-2015 17:04:39.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'ef151505-d0c0-4437-9a0c-f0431028b2ab');
insert into T_XT_YH_JS (uuid, yh_dm, js_dm, mrjs_bj, lr_sj, lrry_dm)
values ('66f19555-37fd-11e4-8f0a-082e5f19f7d7', '284f067c-e934-4395-b794-8c9dc9cf03fe', '1', '1', to_timestamp('19-10-2015 17:04:39.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'ef151505-d0c0-4437-9a0c-f0431028b2ab');
insert into T_XT_YH_JS (uuid, yh_dm, js_dm, mrjs_bj, lr_sj, lrry_dm)
values ('8e81d28e-33d1-11e4-a056-082e5f19f7d7', 'ef151505-d0c0-4437-9a0c-f0431028b2ab', 'b7e5853f-dab6-4375-bdba-cde3b8d6c832', '1', to_timestamp('19-10-2015 17:04:39.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'ef151505-d0c0-4437-9a0c-f0431028b2ab');
commit;
prompt 4 records loaded
prompt Enabling triggers for T_DSQGL_RWJBXX...
alter table T_DSQGL_RWJBXX enable all triggers;
prompt Enabling triggers for T_DSQGL_RWYXJL...
alter table T_DSQGL_RWYXJL enable all triggers;
prompt Enabling triggers for T_DSQGL_RWZXLXX...
alter table T_DSQGL_RWZXLXX enable all triggers;
prompt Enabling triggers for T_XT_BM...
alter table T_XT_BM enable all triggers;
prompt Enabling triggers for T_XT_CD...
alter table T_XT_CD enable all triggers;
prompt Enabling triggers for T_XT_GW...
alter table T_XT_GW enable all triggers;
prompt Enabling triggers for T_XT_JS...
alter table T_XT_JS enable all triggers;
prompt Enabling triggers for T_XT_JS_CD...
alter table T_XT_JS_CD enable all triggers;
prompt Enabling triggers for T_XT_YH...
alter table T_XT_YH enable all triggers;
prompt Enabling triggers for T_XT_YH_BM...
alter table T_XT_YH_BM enable all triggers;
prompt Enabling triggers for T_XT_YH_EWCD...
alter table T_XT_YH_EWCD enable all triggers;
prompt Enabling triggers for T_XT_YH_GW...
alter table T_XT_YH_GW enable all triggers;
prompt Enabling triggers for T_XT_YH_JS...
alter table T_XT_YH_JS enable all triggers;
set feedback on
set define on
prompt Done.
