package com.tjcj.carrental.action;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.tjcj.carrental.model.Carsrc;
import com.tjcj.carrental.model.Goodssrc;
import com.tjcj.carrental.model.Order;
import com.tjcj.carrental.model.User;

public class OrderAction extends BaseAction<Order> implements IBaseAction<Order> {
    private List<Order> items;
    private List<Goodssrc> items1;
    private List<Carsrc> items2;
    private List<Order> items3;

    @Override
    public boolean add(Order entity) {
        // TODO Auto-generated method stub
        return modify("addOrder", entity);
    }

    @Override
    public boolean delete(Order entity) {
        // TODO Auto-generated method stub
        return modify("deleteOrder", entity);
    }

    @Override
    public boolean update(Order entity) {
        // TODO Auto-generated method stub
        return modify("updateOrder", entity);
    }

    @Override
    public List<Order> findAll(int num) {
        return query("findAll", num);
//        return test();
    }

    /*@Override
    public List<Order> findAll() {
        // TODO Auto-generated method stub
//        return query("findAllOrder");
        return test();
    }*/

    public List<Order> findGoodsSrcOrder(int id) {
        // TODO Auto-generated method stub
		return query("findGoodsSrcOrder",id);
//        return test();
    }

    public List<Order> findCarSrcOrder(int id) {
        // TODO Auto-generated method stub
        return query("findCarSrcOrder",id);
//        return test();
    }

    public List<Order> findOrderByGoods(int goodsId){
        return query("findOrderByGoods", goodsId);
    }

    public List<Order> findOrderByCar(int carId){
        return query("findOrderByCar", carId);
    }
    ///

    public List<Order> findPageCarOrder(String pageNumber) {
        // TODO Auto-generated method stub
//		return query("findPageCarOrder",pageNumber);
        return test();
    }

    public List<Order> findPageGoodsOrder(String pageNumber) {
        // TODO Auto-generated method stub
//        return query("findPageGoodsOrder", pageNumber);
        return test();
    }

    public List<Goodssrc> findGoodsTest() {
        items1 = new ArrayList<Goodssrc>();
//        items1.add(GoodsPubActivity.goodssrc);
        return items1;
    }

    public List<Carsrc> findTrucksTest() {
        items2 = new ArrayList<Carsrc>();
//        items2.add(TruckPubActivity.carsrc);
        return items2;
    }

    public List<Goodssrc> findMineSrcTest() {
        items1 = new ArrayList<Goodssrc>();
//        items1.add(GoodsPubActivity.goodssrc);
        return items1;
    }


    //车主接货竞价
    public void newOrder(Goodssrc goodssrc,User user) {
        items3 = new ArrayList<Order>();
        Order order = new Order();
        order.setGoodssrc(goodssrc);
        order.setUserByUserid(user);
        items3.add(order);
    }

    //测试货源竞价列表
    public List<Order> findBiding(){
        return items3;
    }
    //车主常用-我接的货
    public List<Order> findMyGetGoods() {
        return items3;
    }

    public List<Order> test() {
        items = new ArrayList<Order>();

        User u = new User();
        u.setId(4);
        Order o = new Order();
        Carsrc c = new Carsrc();
        Goodssrc g = new Goodssrc();

        o.setCreateTime(new Timestamp(new Date().getTime()));
        int r = (int) (Math.random() * 10000);
        String s = "" + o.getCreateTime().getYear() + o.getCreateTime().getMonth() + o.getCreateTime().getDay()
                + o.getCreateTime().getHours() + o.getCreateTime().getMinutes() + o.getCreateTime().getSeconds();
        o.setNumber("" + r + s + u.getId());
        o.setStatus(1);
        o.setUserByUserid(u);
        o.setId(123);
        c.setMobile("12312312");
        c.setOrdertime(new Timestamp(new Date().getTime()));
        c.setStartCity("手动阀");
        c.setStartArea("斯蒂芬");
        c.setEndCity("水电费");
        c.setEndArea("加哦你");
        g.setStartArea("北辰区");
        g.setStartCity("天津市");
        g.setEndArea("静海");
        g.setEndCity("天津市");
//        c.setOrder(o);
        o.setCarsrc(c);
        o.setGoodssrc(g);

        items.add(o);
        return items;
    }
}
