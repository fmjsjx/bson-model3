package com.github.fmjsjx.bson.model3.core;

import com.alibaba.fastjson2.JSONFactory;
import com.github.fmjsjx.bson.model3.core.model.Equipment;
import com.github.fmjsjx.bson.model3.core.model.Player;
import com.github.fmjsjx.libcommon.json.Fastjson2Library;
import com.github.fmjsjx.libcommon.json.Jackson3Library;
import com.github.fmjsjx.libcommon.json.JsoniterLibrary;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.stream.LongStream;

public class TestModel {

    @Test
    void testPlayer() {
        JSONFactory.setDefaultObjectSupplier(LinkedHashMap::new);

        var player = new Player();
        player.setId(12306);
        player.getBasicInfo().setName("Tester A");
        player.getBasicInfo().setAvatar("https://avatar.test.com/12306");
        player.getBasicInfo().setBirthday(LocalDate.of(2000, 1, 23));
        player.getBasicInfo().setCreatedTime(LocalDateTime.now());
        player.getWallet().setCoinTotal(1000);
        player.getWallet().setCoinConsumed(200);
        player.getWallet().setDiamondTotal(200);
        player.getWallet().setDiamondConsumed(0);
        var equipment0 = new Equipment();
        equipment0.setId("7fa43d22-8801-4bbd-a3a5-77e43c603b33");
        equipment0.setRefId(600001);
        equipment0.setAtk(10);
        equipment0.setDef(2);
        equipment0.setHp(0);
        player.getEquipments().put(equipment0.getId(), equipment0);
        var equipment1 = new Equipment();
        equipment1.setId("f4b849d5-95f7-4714-8f40-3f760f7eee54");
        equipment1.setRefId(600002);
        equipment1.setAtk(0);
        equipment1.setDef(10);
        equipment1.setHp(20);
        player.getEquipments().put(equipment1.getId(), equipment1);
        System.err.println(player);
        var json = player.jsonMarshal(Fastjson2Library.getInstance());
        System.err.println(json);
        var player2 = new Player().jsonUnmarshal(JsoniterLibrary.getInstance(), json);
        System.err.println(player2);
        var player3 = new Player().jsonUnmarshal(Fastjson2Library.getInstance(), json);
        System.err.println(player3);

        // test performance
        long[] t1 = new long[5];
        long[] t2 = new long[5];
        long[] t3 = new long[5];
        for (var index = 0; index < t1.length; index++) {
            var t = System.nanoTime();
            for (var i = 0; i < 1_000_000; i++) {
                new Player().jsonUnmarshal(Jackson3Library.getInstance(), json);
            }
            t1[index] = System.nanoTime() - t;
            t = System.nanoTime();
            for (var i = 0; i < 1_000_000; i++) {
                new Player().jsonUnmarshal(Fastjson2Library.getInstance(), json);
            }
            t2[index] = System.nanoTime() - t;
            t = System.nanoTime();
            for (var i = 0; i < 1_000_000; i++) {
                new Player().jsonUnmarshal(JsoniterLibrary.getInstance(), json);
            }
            t3[index] = System.nanoTime() - t;
        }
        System.err.println("----------  Jackson3  ----------");
        LongStream.of(t1).sorted().forEach(System.out::println);
        System.err.println("--------------------------------");
        System.err.println("----------  Fastjson2  ---------");
        LongStream.of(t2).sorted().forEach(System.out::println);
        System.err.println("--------------------------------");
        System.err.println("----------  JsonIter  ----------");
        LongStream.of(t3).sorted().forEach(System.out::println);
        System.err.println("--------------------------------");
        System.err.println("================================");

        long[] d1 = new long[5];
        long[] d2 = new long[5];
        for (var index = 0; index < d1.length; index++) {
            var t = System.nanoTime();
            for (var i = 0; i < 1_000_000; i++) {
                json = new Player().jsonMarshal(Fastjson2Library.getInstance());
            }
            d1[index] = System.nanoTime() - t;
            t = System.nanoTime();
            for (var i = 0; i < 1_000_000; i++) {
                json = new Player().jsonMarshal(Jackson3Library.getInstance());
            }
            d2[index] = System.nanoTime() - t;
        }
        System.err.println("----------  Fastjson2  ---------");
        LongStream.of(d1).sorted().forEach(System.out::println);
        System.err.println("--------------------------------");
        System.err.println("----------  Jackson3  ----------");
        LongStream.of(d2).sorted().forEach(System.out::println);
        System.err.println("--------------------------------");
        System.err.println("================================");
        System.err.println(json);
    }

}
