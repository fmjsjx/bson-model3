package com.github.fmjsjx.bson.model3.core;

import com.alibaba.fastjson2.JSONFactory;
import com.github.fmjsjx.bson.model3.core.model.Equipment;
import com.github.fmjsjx.bson.model3.core.model.Player;
import com.github.fmjsjx.libcommon.json.Fastjson2Library;
import com.github.fmjsjx.libcommon.json.Jackson3Library;
import com.github.fmjsjx.libcommon.json.JsoniterLibrary;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
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
        player.getPreferences().setCustom("""
                {"key1":"value1","key2":"value2"}""");
        player.getPreferences().setFeatures(List.of("guide", "arena"));
        player.getPreferences().getAttributes().put("channel", "test");
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
        player.getItems().put(400001, 10);
        player.getItems().put(400002, 5);
        player.getItems().put(500001, 1);
        player.increaseUpdatedVersion();
        System.err.println(player);
        var json = player.jsonMarshal(Fastjson2Library.getInstance());
        System.err.println(json);
        var jsonBytes = player.jsonMarshalToBytes(Fastjson2Library.getInstance());
        System.err.println(new String(jsonBytes, StandardCharsets.UTF_8));
        var player2 = new Player().jsonUnmarshal(JsoniterLibrary.getInstance(), json);
        System.err.println(player2);
        System.err.println(new Player().jsonUnmarshal(JsoniterLibrary.getInstance(), jsonBytes));
        var player3 = new Player().jsonUnmarshal(Fastjson2Library.getInstance(), json);
        System.err.println(player3);
        System.err.println(new Player().jsonUnmarshal(Fastjson2Library.getInstance(), jsonBytes));

        // test performance
        long[] t1 = new long[5];
        long[] t2 = new long[5];
        long[] t3 = new long[5];
        long[] t4 = new long[5];
        long[] t5 = new long[5];
        long[] t6 = new long[5];
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
            t = System.nanoTime();
            for (var i = 0; i < 1_000_000; i++) {
                new Player().jsonUnmarshal(Jackson3Library.getInstance(), jsonBytes);
            }
            t4[index] = System.nanoTime() - t;
            t = System.nanoTime();
            for (var i = 0; i < 1_000_000; i++) {
                new Player().jsonUnmarshal(Fastjson2Library.getInstance(), jsonBytes);
            }
            t5[index] = System.nanoTime() - t;
            t = System.nanoTime();
            for (var i = 0; i < 1_000_000; i++) {
                new Player().jsonUnmarshal(JsoniterLibrary.getInstance(), jsonBytes);
            }
            t6[index] = System.nanoTime() - t;
        }
        System.err.println("----------  Jackson3 string  ----------");
        LongStream.of(t1).sorted().forEach(System.err::println);
        System.err.println("----------  Jackson3 bytes  ------------");
        LongStream.of(t4).sorted().forEach(System.err::println);
        System.err.println("----------------------------------------");
        System.err.println("----------  Fastjson2 string  ----------");
        LongStream.of(t2).sorted().forEach(System.err::println);
        System.err.println("----------  Fastjson2 bytes  ------------");
        LongStream.of(t5).sorted().forEach(System.err::println);
        System.err.println("-----------------------------------------");
        System.err.println("----------  Jsoniter string  -----------");
        LongStream.of(t3).sorted().forEach(System.err::println);
        System.err.println("----------  Jsoniter bytes  ------------");
        LongStream.of(t6).sorted().forEach(System.err::println);
        System.err.println("----------------------------------------");
        System.err.println("========================================");

        long[] d1 = new long[5];
        long[] d2 = new long[5];
        long[] d3 = new long[5];
        long[] d4 = new long[5];
        long[] d5 = new long[5];
        long[] d6 = new long[5];
        for (var index = 0; index < d1.length; index++) {
            var t = System.nanoTime();
            for (var i = 0; i < 1_000_000; i++) {
                json = player.jsonMarshal(Jackson3Library.getInstance());
            }
            d1[index] = System.nanoTime() - t;
            t = System.nanoTime();
            for (var i = 0; i < 1_000_000; i++) {
                json = player.jsonMarshal(Fastjson2Library.getInstance());
            }
            d2[index] = System.nanoTime() - t;
            t = System.nanoTime();
            for (var i = 0; i < 1_000_000; i++) {
                json = player.jsonMarshal(JsoniterLibrary.getInstance());
            }
            d3[index] = System.nanoTime() - t;
            t = System.nanoTime();
            for (var i = 0; i < 1_000_000; i++) {
                jsonBytes = player.jsonMarshalToBytes(Jackson3Library.getInstance());
            }
            d4[index] = System.nanoTime() - t;
            t = System.nanoTime();
            for (var i = 0; i < 1_000_000; i++) {
                jsonBytes = player.jsonMarshalToBytes(Fastjson2Library.getInstance());
            }
            d5[index] = System.nanoTime() - t;
            t = System.nanoTime();
            for (var i = 0; i < 1_000_000; i++) {
                jsonBytes = player.jsonMarshalToBytes(JsoniterLibrary.getInstance());
            }
            d6[index] = System.nanoTime() - t;
        }
        System.err.println("----------  Jackson3 string  -----------");
        LongStream.of(d1).sorted().forEach(System.err::println);
        System.err.println("----------  Jackson3 bytes  ------------");
        LongStream.of(d4).sorted().forEach(System.err::println);
        System.err.println("----------------------------------------");
        System.err.println("----------  Fastjson2 string  ----------");
        LongStream.of(d2).sorted().forEach(System.err::println);
        System.err.println("----------  Fastjson2 bytes  -----------");
        LongStream.of(d5).sorted().forEach(System.err::println);
        System.err.println("----------------------------------------");
        System.err.println("========================================");
        System.err.println("----------  Jsoniter string  -----------");
        LongStream.of(d3).sorted().forEach(System.err::println);
        System.err.println("----------  Jsoniter bytes  ------------");
        LongStream.of(d6).sorted().forEach(System.err::println);
        System.err.println("----------------------------------------");
        System.err.println("========================================");
        System.err.println(json);
        System.err.println(new String(jsonBytes, StandardCharsets.UTF_8));
    }

}
