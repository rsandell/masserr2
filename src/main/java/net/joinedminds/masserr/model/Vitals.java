/*
 * The MIT License
 *
 * Copyright (c) 2004-2012-, Robert Sandell-sandell.robert@gmail.com. All rights reserved.
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

import net.joinedminds.masserr.Messages;
import org.jvnet.localizer.Localizable;

/**
 * Description
 *
 * Created: 2004-mar-08 14:26:57
 * @author <a href="mailto:sandell.robert@gmail.com">Robert "Bobby" Sandell</a>
 */
public enum Vitals {

    NORMAL(Messages._vital_Normal()),
    TORPOR(Messages._vital_Torpor()),
    FINAL_DEATH(Messages._vital_FinalDeath()),
    DISAPPEARED(Messages._vital_Disappeared()),
    ON_LONG_JOURNEY(Messages._vital_OnLongJourney());

    private Localizable display;

    private Vitals(Localizable display) {
        this.display = display;
    }

    public Localizable getDisplay() {
        return display;
    }

    public String toString() {
        return getDisplay().toString();
    }

    public boolean isFinalDeath() {
        return (this == FINAL_DEATH);
    }
}
