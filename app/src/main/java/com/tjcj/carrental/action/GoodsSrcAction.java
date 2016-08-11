package com.tjcj.carrental.action;

import com.tjcj.carrental.model.Goodssrc;

import java.util.List;

/**
 * Created by wang on 16-8-2.
 */
public class GoodsSrcAction extends BaseAction implements IBaseAction<Goodssrc>{


    @Override
    public boolean add(Goodssrc entity) {
        return modify("addGoodsSrc", entity);
    }

    @Override
    public boolean delete(Goodssrc entity) {
        return modify("deleteGoodsSrc", entity);
    }

    @Override
    public boolean update(Goodssrc entity) {
        return modify("updateGoodsSrc", entity);
    }

    @Override
    public List<Goodssrc> findAll(int num) {
        return query("findAllGoodsSrc", num);
    }

    public List<Goodssrc> findAllByUserId(int id) {
        return query("findAllByUserIdGoodsSrc", id);
    }

    public List<Goodssrc> findAllByCityGoodsSrc(String city) {
        return query("findAllByCityGoodsSrc", city);
    }

}
