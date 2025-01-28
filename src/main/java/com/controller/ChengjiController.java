package com.controller;


import java.text.SimpleDateFormat;
import com.alibaba.fastjson.JSONObject;
import java.util.*;
import org.springframework.beans.BeanUtils;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.context.ContextLoader;
import javax.servlet.ServletContext;
import com.service.TokenService;
import com.utils.StringUtil;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.lang3.StringUtils;
import com.annotation.IgnoreAuth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;

import com.entity.ChengjiEntity;

import com.service.ChengjiService;
import com.entity.view.ChengjiView;
import com.service.YonghuService;
import com.entity.YonghuEntity;
import com.utils.PageUtils;
import com.utils.R;

/**
 * 成绩
 * 后端接口
 * @author
 * @email
 * @date 2021-03-03
*/
@RestController
@Controller
@RequestMapping("/chengji")
public class ChengjiController {
    private static final Logger logger = LoggerFactory.getLogger(ChengjiController.class);

    @Autowired
    private ChengjiService chengjiService;


    @Autowired
    private TokenService tokenService;


    //级联表service
    @Autowired
    private YonghuService yonghuService;

    //字典表map
    Map<String, Map<Integer, String>> dictionaryMap;

    /**
    * 后端列表
    */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params, HttpServletRequest request){
    logger.debug("page方法:,,Controller:{},,params:{}",this.getClass().getName(),JSONObject.toJSONString(params));
        String role = String.valueOf(request.getSession().getAttribute("role"));
        if(StringUtil.isNotEmpty(role) && "学生".equals(role)){
            params.put("yonghuId",request.getSession().getAttribute("userId"));
        }
    PageUtils page = chengjiService.queryPage(params);

    //字典表数据转换
    List<ChengjiView> list =(List<ChengjiView>)page.getList();
        ServletContext servletContext = ContextLoader.getCurrentWebApplicationContext().getServletContext();
        dictionaryMap = (Map<String, Map<Integer, String>>) servletContext.getAttribute("dictionaryMap");
        for(ChengjiView c:list){
        this.dictionaryConvert(c);
        }
        return R.ok().put("data", page);
    }
    /**
    * 后端详情
    */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        logger.debug("info方法:,,Controller:{},,id:{}",this.getClass().getName(),id);
        ChengjiEntity chengji = chengjiService.selectById(id);
        if(chengji !=null){
            //entity转view
            ChengjiView view = new ChengjiView();
            BeanUtils.copyProperties( chengji , view );//把实体数据重构到view中

            //级联表
            YonghuEntity yonghu = yonghuService.selectById(chengji.getYonghuId());
            if(yonghu != null){
                BeanUtils.copyProperties( yonghu , view ,new String[]{ "id", "createDate"});//把级联的数据添加到view中,并排除id和创建时间字段
                view.setYonghuId(yonghu.getId());
            }
            //字典表字典转换
            ServletContext servletContext = ContextLoader.getCurrentWebApplicationContext().getServletContext();
            dictionaryMap = (Map<String, Map<Integer, String>>) servletContext.getAttribute("dictionaryMap");
            this.dictionaryConvert(view);
            return R.ok().put("data", view);
        }else {
            return R.error(511,"查不到数据");
        }

    }

    /**
    * 后端保存
    */
    @RequestMapping("/save")
    public R save(@RequestBody ChengjiEntity chengji, HttpServletRequest request){
        logger.debug("save方法:,,Controller:{},,chengji:{}",this.getClass().getName(),chengji.toString());
        Wrapper<ChengjiEntity> queryWrapper = new EntityWrapper<ChengjiEntity>()
            .eq("yonghu_id", chengji.getYonghuId())
            .eq("exam_name", chengji.getExamName())
            .eq("kemu_types", chengji.getKemuTypes())
            ;
        logger.info("sql语句:"+queryWrapper.getSqlSegment());
        ChengjiEntity chengjiEntity = chengjiService.selectOne(queryWrapper);
        if(chengjiEntity==null){
            chengji.setCreateTime(new Date());
        //  String role = String.valueOf(request.getSession().getAttribute("role"));
        //  if("".equals(role)){
        //      chengji.set
        //  }
            chengjiService.insert(chengji);
            return R.ok();
        }else {
            return R.error(511,"该学生此次考试本门成绩已经录入过");
        }
    }

    /**
    * 修改
    */
    @RequestMapping("/update")
    public R update(@RequestBody ChengjiEntity chengji, HttpServletRequest request){
        logger.debug("update方法:,,Controller:{},,chengji:{}",this.getClass().getName(),chengji.toString());
        //根据字段查询是否有相同数据
        Wrapper<ChengjiEntity> queryWrapper = new EntityWrapper<ChengjiEntity>()
            .notIn("id",chengji.getId())
            .eq("yonghu_id", chengji.getYonghuId())
            .eq("exam_name", chengji.getExamName())
            .eq("kemu_types", chengji.getKemuTypes())
            ;
        logger.info("sql语句:"+queryWrapper.getSqlSegment());
        ChengjiEntity chengjiEntity = chengjiService.selectOne(queryWrapper);
        if(chengjiEntity==null){
            //  String role = String.valueOf(request.getSession().getAttribute("role"));
            //  if("".equals(role)){
            //      chengji.set
            //  }
            chengjiService.updateById(chengji);//根据id更新
            return R.ok();
        }else {
            return R.error(511,"该学生本门考试已经录入过");
        }
    }


    /**
    * 删除
    */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] ids){
        logger.debug("delete:,,Controller:{},,ids:{}",this.getClass().getName(),ids.toString());
        chengjiService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }

    /**
    *字典表数据转换
    */
    public void dictionaryConvert(ChengjiView chengjiView){
        //当前表的字典字段
        if(StringUtil.isNotEmpty(String.valueOf(chengjiView.getKemuTypes()))){
            chengjiView.setKemuValue(dictionaryMap.get("kemu_types").get(chengjiView.getKemuTypes()));
        }

        //级联表的字典字段
        if(StringUtil.isNotEmpty(String.valueOf(chengjiView.getSexTypes()))){
            chengjiView.setSexValue(dictionaryMap.get("sex_types").get(chengjiView.getSexTypes()));
        }
    }



    /**
     * 柱状图统计统计
     */
    @RequestMapping("/group/bar")
    @ResponseBody
    public R groupBar() {
        logger.debug("group:,,Controller:{}",this.getClass().getName());
        List<HashMap<String, Object>> map1 = chengjiService.groupBar();
        if(map1!= null && map1.size()>0){

            //以考试名字分组  如  第一次考试 科目1    第二次考试  科目2   把科目1和科目2合并
            Map<String, HashMap<Integer, Double>> map2 = new HashMap<>();//map2 就是可以考试名字作为key  以科目作为vale的值   科目是科目类型为key,平均分数为value
            for(HashMap<String, Object> m:map1){
                Double avg = Double.parseDouble(m.get("avg").toString());//平均分数
                Integer kemu_types = Integer.parseInt(m.get("kemu_types").toString());//科目id
                String exam_name = String.valueOf(m.get("exam_name"));//考试名字
                HashMap<Integer, Double> oldKemu = map2.get(exam_name);
                if( oldKemu==null){
                    //map2中没有这个对象就说明这个考试名字的还没有加入
                    //      科目      平均成绩
                    HashMap<Integer, Double> kemu = new HashMap<>();
                    //以科目为id,以成绩为value
                    kemu.put(kemu_types,avg);
                    map2.put(exam_name,kemu);
                }else{
                    //已经有这个考试名字
                    oldKemu.put(kemu_types,avg);
                }
            }


            //插入空余科目,比如数学这次没有考试给生成0
            ServletContext servletContext = ContextLoader.getCurrentWebApplicationContext().getServletContext();
            dictionaryMap = (Map<String, Map<Integer, String>>) servletContext.getAttribute("dictionaryMap");
            Map<Integer, String> kemu_types = dictionaryMap.get("kemu_types");
            Set<Integer> integers1 = kemu_types.keySet(); // 1 2 3
            Set<String> strings = map2.keySet();
            for(String s:strings){
                HashMap<Integer, Double> integerDoubleHashMap = map2.get(s);
                Set<Integer> integers = integerDoubleHashMap.keySet(); // 2 3
                for(Integer i:integers1){
                    if(!integers.contains(i)){
                        //查出的科目不包含当前科目
                        integerDoubleHashMap.put(i,0.0);
                    }
                }
            }

            //生成最后回显数据
            Map<String, Object> result = new HashMap<>();
            List<String> categ = new ArrayList<>();
            List<List<Double>> data = new ArrayList<>();
            Map<Integer, List<Double>> kemuMap = new HashMap<>();
            for(Integer a:integers1){
                for(String s:strings ){
                    if(kemuMap.get(a)==null){
                        List<Double> doubles = new ArrayList<>();
                        doubles.add(map2.get(s).get(a));
                        kemuMap.put(a,doubles);
                    }else{
                        kemuMap.get(a).add(map2.get(s).get(a));
                    }
                }
                categ.add(kemu_types.get(a));
            }
            Set<Integer> s = kemuMap.keySet();
            for(Integer q:s){
                data.add(kemuMap.get(q));
            }
            List<String> name = new ArrayList<>(strings);
            result.put("title","平均分数");// 统计的什么东西
            result.put("name",name);//x轴最下面的名字
            result.put("categ",categ);//类别
            result.put("data",data);//数据
            return R.ok().put("data", result);
/*
    {
        "data": [
            [0.0, 19.0],
            [10.0, 10.0],
            [58.0, 0.0]
        ],
        "name": ["第一次考试", "第二次考试"],
        "categ": ["数学", "语文", "英语"],
        "title": "平均分数"
    }
*/
        }else{
            return R.error("数据为空");
        }


    }


}

