/*
 * The MIT License
 *
 * Copyright (c) 2013-, Robert Sandell-sandell.robert@gmail.com. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package net.joinedminds.masserr.model;

import com.github.jmkgreen.morphia.annotations.Embedded;

/**
 * Description
 *
 * @author Robert Sandell &lt;sandell.robert@gmail.com&gt;
 */
@Embedded
public class Virtues {

    private Adherence adherence;
    private int adherenceDots;
    private Resistance resistance;
    private int resistanceDots;
    private int courageDots;

    public Virtues(Adherence adherence, int adherenceDots, Resistance resistance, int resistanceDots, int courageDots) {
        this.adherence = adherence;
        this.adherenceDots = adherenceDots;
        this.resistance = resistance;
        this.resistanceDots = resistanceDots;
        this.courageDots = courageDots;
    }

    public Virtues(Morality morality, int adherenceDots, int resistanceDots, int courageDots) {
        this.adherenceDots = adherenceDots;
        this.resistanceDots = resistanceDots;
        this.courageDots = courageDots;
        this.adherence = morality.getAdherenceTeachings();
        this.resistance = morality.getResistanceTeachings();
    }

    public Virtues() {
    }

    public Adherence getAdherence() {
        return adherence;
    }

    public void setAdherence(Adherence adherence) {
        this.adherence = adherence;
    }

    public int getAdherenceDots() {
        return adherenceDots;
    }

    public void setAdherenceDots(int adherenceDots) {
        this.adherenceDots = adherenceDots;
    }

    public Resistance getResistance() {
        return resistance;
    }

    public void setResistance(Resistance resistance) {
        this.resistance = resistance;
    }

    public int getResistanceDots() {
        return resistanceDots;
    }

    public void setResistanceDots(int resistanceDots) {
        this.resistanceDots = resistanceDots;
    }

    public int getCourageDots() {
        return courageDots;
    }

    public void setCourageDots(int courageDots) {
        this.courageDots = courageDots;
    }

    public static enum Adherence {
        Conscience, Conviction
    }

    public static enum Resistance {
        SelfControl, Instinct
    }
}
