<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
var menus = [

	{
        "backMenu":[
            {
                "child":[
                    {
                        "buttons":[
                            "新增",
                            "修改",
                            "删除"
                        ],
                        "menu":"管理员管理",
                        "menuJump":"列表",
                        "tableName":"users"
                    }
                ],
                "menu":"管理员信息"
            }
            ,{
                "child":[
                    {
                        "buttons":[
                            "查看",
                            "新增",
                            "修改",
                            "删除"
                        ],
                        "menu":"学生信息管理",
                        "menuJump":"列表",
                        "tableName":"yonghu"
                    },
					{
                        "buttons":[
                            "查看",
                            "修改",
                            "报表",
                            "删除"
                        ],
                        "menu":"请假管理",
                        "menuJump":"列表",
                        "tableName":"qingjia"
                    },
					{
                        "buttons":[
                            "查看",
                            "新增",
                            "修改",
                            "删除"
                        ],
                        "menu":"班会通知管理",
                        "menuJump":"列表",
                        "tableName":"tongzhi"
                    },
					{
                        "buttons":[
                            "查看",
                            "新增",
                            "修改",
                            "报表",
                            "删除"
                        ],
                        "menu":"成绩管理",
                        "menuJump":"列表",
                        "tableName":"chengji"
                    }
                ],
                "menu":"学生管理"
            }
			,{
                "child":[
                    {
                        "buttons":[
                            "查看",
                            "新增",
                            "修改",
                            "删除"
                        ],
                        "menu":"家长信息管理",
                        "menuJump":"列表",
                        "tableName":"jiazhang"
                    },{
                        "buttons":[
                            "查看",
                            "新增",
                            "修改",
                            "删除"
                        ],
                        "menu":"家长交流管理",
                        "menuJump":"列表",
                        "tableName":"jiazhangjiaoliu"
                    }
                ],
                "menu":"家长管理"
            }
            ,{
                "child":[
                    {
                        "buttons":[
                            "查看",
                            "新增",
                            "修改",
                            "删除"
                        ],
                        "menu":"住宿管理",
                        "menuJump":"列表",
                        "tableName":"zhusu"
                    }
                ],
                "menu":"住宿管理"
            }
            ,{
                "child":[
                    {
                        "buttons":[
                            "查看",
                            "新增",
                            "修改",
                            "删除"
                        ],
                        "menu":"学业预警管理",
                        "menuJump":"列表",
                        "tableName":"yujing"
                    }
					
                ],
                "menu":"学业预警管理"
            }
            ,{
                "child":[
                    {
                        "buttons":[
                            "查看",
                            "新增",
                            "修改",
                            "删除"
                        ],
                        "menu":"实习管理",
                        "menuJump":"列表",
                        "tableName":"shixi"
                    }
					
                ],
                "menu":"实习管理"
            }
            ,{
                "child":[
                    {
                        "buttons":[
                            "查看",
                            "删除"
                        ],
                        "menu":"校友交流管理",
                        "menuJump":"列表",
                        "tableName":"xiaoyoujiaoliu"
                    }
                ],
                "menu":"校友交流管理"
            }
        ],
        "frontMenu":[

        ],
        "roleName":"管理员",
        "tableName":"users"
    }
	,{
        "backMenu":[
            {
                "child":[
                    {
                        "buttons":[
                            "查看",
                            "新增",
                            "修改",
                            "删除"
                        ],
                        "menu":"家长交流管理",
                        "menuJump":"列表",
                        "tableName":"jiazhangjiaoliu"
                    }
                ],
                "menu":"家长交流管理"
            }
        ],
        "frontMenu":[

        ],
        "roleName":"家长",
        "tableName":"jiazhang"
    }
	
	,{
        "backMenu":[
            {
                "child":[
                    {
                        "buttons":[
                            "查看",
                            "新增",
                            "修改"
                        ],
                        "menu":"请假管理",
                        "menuJump":"列表",
                        "tableName":"qingjia"
                    }
                ],
                "menu":"请假管理"
            }
            ,{
                "child":[
                    {
                        "buttons":[
                            "查看"
                        ],
                        "menu":"班会通知管理",
                        "menuJump":"列表",
                        "tableName":"tongzhi"
                    }
                ],
                "menu":"班会通知管理"
            }
            ,{
                "child":[
                    {
                        "buttons":[
                            "查看"
                        ],
                        "menu":"成绩管理",
                        "menuJump":"列表",
                        "tableName":"chengji"
                    }
                ],
                "menu":"成绩管理"
            }
            ,{
                "child":[
                    {
                        "buttons":[
                            "查看"
                        ],
                        "menu":"住宿管理",
                        "menuJump":"列表",
                        "tableName":"zhusu"
                    }
                ],
                "menu":"住宿管理"
            }
            ,{
                "child":[
                    {
                        "buttons":[
                            "查看"
                        ],
                        "menu":"学业预警管理",
                        "menuJump":"列表",
                        "tableName":"yujing"
                    }
                ],
                "menu":"学业预警管理"
            }
            ,{
                "child":[
                    {
                        "buttons":[
                            "查看"
                        ],
                        "menu":"实习管理",
                        "menuJump":"列表",
                        "tableName":"shixi"
                    }
                ],
                "menu":"实习管理"
            }
            ,{
                "child":[
                    {
                        "buttons":[
                            "查看",
                            "新增",
                            "修改"
                        ],
                        "menu":"校友交流管理",
                        "menuJump":"列表",
                        "tableName":"xiaoyoujiaoliu"
                    }
                ],
                "menu":"校友交流管理"
            }
        ],
        "frontMenu":[

        ],
        "roleName":"学生",
        "tableName":"yonghu"
    }
];

var hasMessage = '';
