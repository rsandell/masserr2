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


import java.util.*;

/**
 * Description.
 * <p/>
 * Created: 2004-mar-23 19:45:12
 * 
 * @author <a href="mailto:the.bobby.is@home.se>Robert Sandell</a>"
 */
public class EmptyRole {
    private Clan clan;
    private List<Discipline> disciplines;
    private List<String> thaumaturgicalPaths;
    private List<String> necromancyPaths;
    private List<Ability> physicalAbilities;
    private List<Ability> socialAbilities;
    private List<Ability> mentalAbilities;
    private List<MeritOrFlaw> merits;
    private List<MeritOrFlaw> flaws;
    private List<String> derangements;
    private boolean ghoul;
    private List<String> otherTraits;
    private List compiledInfluences;

    public EmptyRole(Clan pClan, List pPhysicalAbilities, List pSocialAbilities, List pMentalAbilities, List<Discipline> pDisciplines, boolean pGhoul) {
        clan = pClan;
        physicalAbilities = pPhysicalAbilities;
        socialAbilities = pSocialAbilities;
        mentalAbilities = pMentalAbilities;
        disciplines = pDisciplines;
        ghoul = pGhoul;
        thaumaturgicalPaths = new ArrayList<String>(7);
        for(int i = 0; i < 6; i++) {
            thaumaturgicalPaths.add("&nbsp;");
        }
        necromancyPaths = new ArrayList<String>(7);
        for(int i = 0; i < 6; i++) {
            necromancyPaths.add("&nbsp;");
        }
        merits = new ArrayList<MeritOrFlaw>(5);
        for (int i = 0; i < 5; i++) {
            merits.add(new MeritOrFlaw(-1, "&nbsp;", 1));
        }
        flaws = new ArrayList<MeritOrFlaw>(5);
        for (int i = 0; i < 5; i++) {
            flaws.add(new MeritOrFlaw(-1, "&nbsp;", -1));
        }
        derangements = new ArrayList<String>(4);
        for (int i = 0; i < 3; i++) {
            derangements.add("&nbsp;");
        }
        otherTraits = new ArrayList<String>(4);
        for (int i = 0; i < 3; i++) {
            otherTraits.add("&nbsp;");
        }
        compiledInfluences = new ArrayList(0);
    }

    public int getId() {
        return -1;
    }
    public String getName() {
        return "Name: ";
    }
    public String getPlayerName() {
        return "&nbsp;";
    }
    public String getGeneration() {
        return "&nbsp;";
    }
    public String getSire() {
        return "&nbsp;";
    }
    public String getEmbraced() {
        return "&nbsp;";
    }
    public String getEmbracedString() {
        return "&nbsp;";
    }
    public Clan getClan() {
        return clan;
    }
    public String getNature() {
        return "&nbsp;";
    }
    public String getDemeanor() {
        return "&nbsp;";
    }
    public String getCourage() {
        return "&nbsp;";
    }
    public String getConcience() {
        return "&nbsp;";
    }
    public String getSelfControl() {
        return "&nbsp;";
    }
    public String getWillpower() {
        return "&nbsp;";
    }
    public String getPathDots() {
        return "&nbsp;";
    }
    public String getPath() {
        return "Path of ...";
    }
    public String getPhysical() {
        return "&nbsp;";
    }
    public String getSocial() {
        return "&nbsp;";
    }
    public String getMental() {
        return "&nbsp;";
    }
    public int getExtraHealthLevels() {
        return 0;
    }
    public boolean isSufferesOfInjury() {
        return true;
    }
    public List<Discipline> getDisciplines() {
        return disciplines;
    }
    public List<String> getThaumaturgicalPaths() {
        return thaumaturgicalPaths;
    }
    public List<String> getNecromancyPaths() {
        return necromancyPaths;
    }
    public List<Ability> getPhysicalAbilities() {
        return physicalAbilities;
    }
    public List<Ability> getSocialAbilities() {
        return socialAbilities;
    }
    public List<Ability> getMentalAbilities() {
        return mentalAbilities;
    }
    public List<MeritOrFlaw> getMerits() {
        return merits;
    }
    public List<MeritOrFlaw> getFlaws() {
        return flaws;
    }
    public List<String> getDerangements() {
        return derangements;
    }
    public List<String> getOtherTraits() {
        return otherTraits;
    }
    public boolean hasThaumaturgy() {
        return true;
    }

    public boolean hasNecromancy() {
        return true;
    }

    public boolean hasRituals() {
        return false;
    }
    public List<RitualType> getRitualTypes() {
        ArrayList<RitualType> list = new ArrayList<RitualType>();
        return list;
    }
    public static int getAge(Date pEmbraced) {
        Calendar cal = Calendar.getInstance();
        Calendar emCal = Calendar.getInstance();
        emCal.setTime(pEmbraced);
        cal.add(Calendar.YEAR, -emCal.get(Calendar.YEAR));
        cal.add(Calendar.MONTH, -emCal.get(Calendar.MONTH));
        cal.add(Calendar.DAY_OF_MONTH, -emCal.get(Calendar.DAY_OF_MONTH));
        return cal.get(Calendar.YEAR);
    }

    public String getAge() {
        return "&nbsp;";
    }
    public boolean isGhoul() {
        return ghoul;
    }

    public Domain getDomain() {
        return null; //AppPreferences.getPreferredDomain();
    }

    public String getSelfControlORinstinct() {
        return "Self-Control/Instinct";
    }

    public String getConcienseORconviction() {
        return "Conciense/Conviction";
    }

    public List compileInfluences() {
        return compiledInfluences;
    }
}
