package com.example.moragame;

import com.example.moragame.game.Mora;

import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void game_test() {

        for (Mora mora:Mora.values()){
            System.out.println(mora);
        }
        for (Mora mora:Mora.values()){
            System.out.println(mora.ordinal());
        }
        Mora mora=Mora.PAPER;
        System.out.println(mora.equals(Mora.PAPER));
    }
}