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

package net.joinedminds.masserr.dataimport;

import com.google.inject.Inject;
import net.joinedminds.masserr.db.*;
import net.joinedminds.masserr.model.*;
import net.joinedminds.masserr.model.Config;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Description
 *
 * @author Robert Sandell &lt;sandell.robert@gmail.com&gt;
 */
public class Importer {

    private static final Logger logger = Logger.getLogger(Importer.class.getName());

    private ManipulationDB manipulationDB;
    private CreateRulesDB createRulesDB;
    private InfluenceDB influenceDB;
    private BankingDB bankingDB;
    private AdminDB adminDB;
    private Map<String, Ability> abilities;
    private HashMap<String, Clan> clans;
    private HashMap<String, Discipline> disciplines;
    private HashMap<String, Generation> generations;
    private HashMap<String, RitualType> ritualTypes;
    private Domain domain;
    private Campaign campaign;

    @Inject
    public Importer(ManipulationDB manipulationDB, CreateRulesDB createRulesDB, InfluenceDB influenceDB,
                    BankingDB bankingDB, AdminDB adminDB) {
        this.manipulationDB = manipulationDB;
        this.createRulesDB = createRulesDB;
        this.influenceDB = influenceDB;
        this.bankingDB = bankingDB;
        this.adminDB = adminDB;
    }

    public void importAll() throws IOException, SAXException, ParserConfigurationException {
        Domain d = manipulationDB.newDomain();
        d.setName("World");
        domain = manipulationDB.saveDomain(d);
        Campaign c = adminDB.newCampaign();
        c.setName("Masserr");
        campaign = adminDB.saveCampaign(c);
        //======ABILITIES======
        importAbilities();
        //======CREATE RULES======
        importCreateRules();
        //======CLANS========
        importClans();
        //========DISCIPLINES=========
        importDisciplines();
        //========CLAN DISCIPLINES======
        importClanDisciplines();
        //======FIGHT OR FLIGHT=========
        importFightOrFlight();
        //======GENERATIONS===========
        importGenerations();
        //=====INFLUENCES==========
        importInfluences();
        //=====Merits and Flaws==========
        importMeritOrFlaws();
        //=====PATHS=======
        importPaths();
        //======OTHER TRAITS========
        importOtherTraits();
        //=====PROFESSIONS======
        importProfessions();
        //=====RITUAL TYPES=======
        importRitualTypes();
        //=========RITUALS===========
        importRituals();
        //=======Starting Money=======
        importStarterMoneyRules();
        importArchetypes();
        importMoralityPaths();
    }

    private void importMoralityPaths() throws IOException, SAXException, ParserConfigurationException {
        logger.info("Importing Morality Paths");
        importFromResource(getClass().getResourceAsStream("/import/morality_paths.xml"), "Query2", "path", null,
                new Creator<Morality>() {
                    @Override
                    public Morality create() {
                        return manipulationDB.newMorality();
                    }
                }, new Saver<Morality>() {
                    @Override
                    public Morality save(Morality entity) {
                        Morality morality = manipulationDB.saveMorality(entity);
                        if(morality.getName().equals("Humanity")) {
                            Config config = adminDB.getConfig();
                            config.setDefaultMorality(morality);
                            adminDB.saveConfig(config);
                        }
                        return morality;
                    }
                }, moralityHandlers()
        );
    }

    private Map<String, AttributeHandler<Morality>> moralityHandlers() {
        Map<String, AttributeHandler<Morality>> map = new HashMap<>();
        map.put("path", new AttributeHandler<Morality>() {
            @Override
            public void handle(Node node, Morality entity) {
                entity.setName(node.getTextContent());
            }
        });
        map.put("adherence", new AttributeHandler<Morality>() {
            @Override
            public void handle(Node node, Morality entity) {
                entity.setAdherenceTeachings(Virtues.Adherence.valueOf(node.getTextContent()));
            }
        });
        map.put("resistance", new AttributeHandler<Morality>() {
            @Override
            public void handle(Node node, Morality entity) {
                entity.setResistanceTeachings(Virtues.Resistance.valueOf(node.getTextContent()));
            }
        });
        return map;
    }

    private void importArchetypes() throws IOException, SAXException, ParserConfigurationException {
        logger.info("Importing Archetypes");
        importFromResource(getClass().getResourceAsStream("/import/archetypes.xml"), "Query2", "nature", null, new Creator<Archetype>() {
                    @Override
                    public Archetype create() {
                        return manipulationDB.newArchetype();
                    }
                }, new Saver<Archetype>() {
                    @Override
                    public Archetype save(Archetype entity) {
                        return manipulationDB.saveArchetype(entity);
                    }
                }, archetypeHandlers()
        );
    }

    private Map<String, AttributeHandler<Archetype>> archetypeHandlers() {
        Map<String, AttributeHandler<Archetype>> map = new HashMap<>();
        map.put("nature", new AttributeHandler<Archetype>() {
            @Override
            public void handle(Node node, Archetype entity) {
                entity.setName(node.getTextContent());
            }
        });
        return map;
    }

    private void importClanDisciplines() throws IOException, SAXException, ParserConfigurationException {
        logger.info("Importing Clan Disciplines");
        if (clans == null) throw new IllegalStateException("Clans must be imported first.");
        mapImportFromResource(getClass().getResourceAsStream("/import/clan_disciplines.xml"), "clan_disciplines", new Mapper<Clan>() {
                    @Override
                    public Clan map(Map<String, String> entity) {
                        String clanId = entity.get("clan_id");
                        String disciplineId = entity.get("discipline_id");
                        Clan c = clans.get(clanId);
                        if (c != null) {
                            Discipline d = disciplines.get(disciplineId);
                            if (d != null) {
                                c.getClanDisciplines().add(d);
                                return c;
                            } else {
                                logger.warning("Could not find discipline with id: " + disciplineId);
                            }
                        } else {
                            logger.warning("Could not find clan with id: " + clanId);
                        }
                        return null;
                    }
                }, new Saver<Clan>() {
                    @Override
                    public Clan save(Clan entity) {
                        return manipulationDB.saveClan(entity);
                    }
                }
        );
    }

    public void importStarterMoneyRules() throws ParserConfigurationException, IOException, SAXException {
        logger.info("Importing StarterMoneyRules");
        Map<String, AttributeHandler<StarterMoneyRule>> handlers = starterMoneyRuleHandlers();
        importFromResource(getClass().getResourceAsStream("/import/roleAgeSpanMoney.xml"), "roleAgeSpanMoney", "id", null,
                new Creator<StarterMoneyRule>() {
                    @Override
                    public StarterMoneyRule create() {
                        return bankingDB.newStarterMoneyRule();
                    }
                },
                new Saver<StarterMoneyRule>() {
                    @Override
                    public StarterMoneyRule save(StarterMoneyRule entity) {
                        return bankingDB.saveStarterMoneyRule(entity);
                    }
                },
                handlers
        );
    }

    public void importRituals() throws ParserConfigurationException, IOException, SAXException {
        logger.info("Importing Rituals");
        if (ritualTypes == null) throw new IllegalStateException("RitualTypes must be imported first.");
        importFromResource(getClass().getResourceAsStream("/import/rituals.xml"), "rituals", "id", null,
                new Creator<Ritual>() {
                    @Override
                    public Ritual create() {
                        return manipulationDB.newRitual();
                    }
                }, new Saver<Ritual>() {
                    @Override
                    public Ritual save(Ritual entity) {
                        return manipulationDB.saveRitual(entity);
                    }
                }, ritualHandlers()
        );
    }

    public void importRitualTypes() throws ParserConfigurationException, IOException, SAXException {
        logger.info("Importing Ritual Types");
        ritualTypes = new HashMap<>();
        importFromResource(getClass().getResourceAsStream("/import/ritual_types.xml"), "ritual_types", "id", ritualTypes,
                new Creator<RitualType>() {
                    @Override
                    public RitualType create() {
                        return manipulationDB.newRitualType();
                    }
                },
                new Saver<RitualType>() {
                    @Override
                    public RitualType save(RitualType entity) {
                        return manipulationDB.saveRitualType(entity);
                    }
                },
                ritualTypeHandlers()
        );
    }

    public void importProfessions() throws ParserConfigurationException, IOException, SAXException {
        logger.info("Importing Professions");
        importFromResource(getClass().getResourceAsStream("/import/professions.xml"), "professions", "id", null,
                new Creator<Profession>() {
                    @Override
                    public Profession create() {
                        return influenceDB.newProfession();
                    }
                }, new Saver<Profession>() {
                    @Override
                    public Profession save(Profession entity) {
                        return influenceDB.saveProfession(entity);
                    }
                }, professionHandlers()
        );
    }

    public void importOtherTraits() throws ParserConfigurationException, IOException, SAXException {
        logger.info("Importing OtherTraits");
        importFromResource(getClass().getResourceAsStream("/import/otherTraits.xml"), "otherTraits", "id", null,
                new Creator<OtherTrait>() {
                    @Override
                    public OtherTrait create() {
                        return manipulationDB.newOtherTrait();
                    }
                }, new Saver<OtherTrait>() {
                    @Override
                    public OtherTrait save(OtherTrait entity) {
                        return manipulationDB.saveOtherTrait(entity);
                    }
                }, otherTraitsHandlers()
        );
    }

    public void importMeritOrFlaws() throws ParserConfigurationException, IOException, SAXException {
        logger.info("Importing MeritOrFlaws");
        importFromResource(getClass().getResourceAsStream("/import/meritsNflaws.xml"), "meritsNflaws", "id", null,
                new Creator<MeritOrFlaw>() {
                    @Override
                    public MeritOrFlaw create() {
                        return manipulationDB.newMeritOrFlaw();
                    }
                },
                new Saver<MeritOrFlaw>() {
                    @Override
                    public MeritOrFlaw save(MeritOrFlaw entity) {
                        return manipulationDB.saveMeritOrFlaw(entity);
                    }
                },
                meritOrFlawHandlers()
        );
    }

    public void importInfluences() throws ParserConfigurationException, IOException, SAXException {
        logger.info("Importing Influences");
        importFromResource(getClass().getResourceAsStream("/import/influences.xml"), "influences", "id", null,
                new Creator<Influence>() {
                    @Override
                    public Influence create() {
                        return influenceDB.newInfluence();
                    }
                },
                new Saver<Influence>() {
                    @Override
                    public Influence save(Influence entity) {
                        return influenceDB.saveInfluence(entity);
                    }
                },
                influenceHandlers()
        );
    }

    public void importGenerations() throws ParserConfigurationException, IOException, SAXException {
        logger.info("Importing Generations");
        generations = new HashMap<>();
        importFromResource(getClass().getResourceAsStream("/import/generations.xml"), "generations", "generation", generations,
                new Creator<Generation>() {
                    @Override
                    public Generation create() {
                        return manipulationDB.newGeneration();
                    }
                }, new Saver<Generation>() {
                    @Override
                    public Generation save(Generation entity) {
                        return manipulationDB.saveGeneration(entity);
                    }
                },
                generationHandlers()
        );
    }

    public void importFightOrFlight() throws ParserConfigurationException, IOException, SAXException {
        logger.info("Importing FightOrFlight");
        importFromResource(getClass().getResourceAsStream("/import/fightNflight.xml"), "fightNflight", "id", null, new Creator<FightOrFlight>() {
                    @Override
                    public FightOrFlight create() {
                        return manipulationDB.newFightOrFlight();
                    }
                }, new Saver<FightOrFlight>() {
                    @Override
                    public FightOrFlight save(FightOrFlight entity) {
                        return manipulationDB.saveFightOrFlight(entity);
                    }
                }, fightOrFlightHandlers()
        );
    }

    public void importDisciplines() throws ParserConfigurationException, IOException, SAXException {
        logger.info("Importing Disciplines");
        if (abilities == null) throw new IllegalStateException("Abilities must be imported first.");
        disciplines = new HashMap<>();
        importFromResource(getClass().getResourceAsStream("/import/disciplines.xml"), "disciplines", "id", disciplines,
                new Creator<Discipline>() {
                    @Override
                    public Discipline create() {
                        return manipulationDB.newDiscipline();
                    }
                },
                new Saver<Discipline>() {
                    @Override
                    public Discipline save(Discipline entity) {
                        return manipulationDB.saveDiscipline(entity);
                    }
                }, disciplineHandlers()
        );
    }

    public void importClans() throws ParserConfigurationException, IOException, SAXException {
        logger.info("Importing Clans");
        clans = new HashMap<>();

        importFromResource(getClass().getResourceAsStream("/import/clans.xml"), "clans", "id", clans,
                new Creator<Clan>() {
                    @Override
                    public Clan create() {
                        return manipulationDB.newClan();
                    }
                }, new Saver<Clan>() {
                    @Override
                    public Clan save(Clan entity) {
                        return manipulationDB.saveClan(entity);
                    }
                }, clanHandlers()
        );
    }

    public void importCreateRules() throws ParserConfigurationException, IOException, SAXException {
        logger.info("Importing CreateRules");
        importFromResource(getClass().getResourceAsStream("/import/age_createRules.xml"), "age_createRules", "id", null,
                new Creator<CreateRule>() {
                    @Override
                    public CreateRule create() {
                        return createRulesDB.newRule();
                    }
                },
                new Saver<CreateRule>() {
                    @Override
                    public CreateRule save(CreateRule entity) {
                        return createRulesDB.saveRule(entity);
                    }
                }, createRulesHandlers()
        );
    }

    public void importAbilities() throws ParserConfigurationException, IOException, SAXException {
        abilities = new HashMap<>();
        logger.info("Importing Abilities");
        importFromResource(getClass().getResourceAsStream("/import/abilities.xml"), "abilities", "id", abilities,
                new Creator<Ability>() {
                    @Override
                    public Ability create() {
                        return manipulationDB.newAbility();
                    }
                },
                new Saver<Ability>() {
                    @Override
                    public Ability save(Ability entity) {
                        logger.log(Level.FINE, "Storing {0}", entity.getName());
                        return manipulationDB.saveAbility(entity);
                    }
                }, abilityHandlers()
        );
    }

    private Map<String, AttributeHandler<StarterMoneyRule>> starterMoneyRuleHandlers() {
        Map<String, AttributeHandler<StarterMoneyRule>> handlers = new HashMap<>();
        handlers.put("min_age", new AttributeHandler<StarterMoneyRule>() {
            @Override
            public void handle(Node node, StarterMoneyRule entity) {
                entity.setMinAge(Integer.parseInt(node.getTextContent()));
            }
        });
        handlers.put("max_age", new AttributeHandler<StarterMoneyRule>() {
            @Override
            public void handle(Node node, StarterMoneyRule entity) {
                entity.setMaxAge(Integer.parseInt(node.getTextContent()));
            }
        });
        handlers.put("base_money", new AttributeHandler<StarterMoneyRule>() {
            @Override
            public void handle(Node node, StarterMoneyRule entity) {
                entity.setBaseMoney(Integer.parseInt(node.getTextContent()));
            }
        });
        return handlers;
    }

    private Map<String, AttributeHandler<Ritual>> ritualHandlers() {
        Map<String, AttributeHandler<Ritual>> handlers = new HashMap<>();
        handlers.put("name", new AttributeHandler<Ritual>() {
            @Override
            public void handle(Node node, Ritual entity) {
                entity.setName(node.getTextContent());
            }
        });
        handlers.put("type", new AttributeHandler<Ritual>() {
            @Override
            public void handle(Node node, Ritual entity) {
                entity.setRitualType(ritualTypes.get(node.getTextContent()));
            }
        });
        handlers.put("level", new AttributeHandler<Ritual>() {
            @Override
            public void handle(Node node, Ritual entity) {
                entity.setLevel(Integer.parseInt(node.getTextContent()));
            }
        });
        handlers.put("description", new AttributeHandler<Ritual>() {
            @Override
            public void handle(Node node, Ritual entity) {
                entity.setDescription(node.getTextContent());
            }
        });
        return handlers;
    }

    private Map<String, AttributeHandler<RitualType>> ritualTypeHandlers() {
        Map<String, AttributeHandler<RitualType>> handlers = new HashMap<>();
        handlers.put("name", new AttributeHandler<RitualType>() {
            @Override
            public void handle(Node node, RitualType entity) {
                entity.setName(node.getTextContent());
            }
        });
        return handlers;
    }

    private Map<String, AttributeHandler<Profession>> professionHandlers() {
        Map<String, AttributeHandler<Profession>> handlers = new HashMap<>();
        handlers.put("name", new AttributeHandler<Profession>() {
            @Override
            public void handle(Node node, Profession entity) {
                entity.setName(node.getTextContent());
            }
        });
        handlers.put("monthly_income", new AttributeHandler<Profession>() {
            @Override
            public void handle(Node node, Profession entity) {
                entity.setMonthlyIncome(Integer.parseInt(node.getTextContent()));
            }
        });
        handlers.put("mortal", new AttributeHandler<Profession>() {
            @Override
            public void handle(Node node, Profession entity) {
                entity.setMortal("1".equals(node.getTextContent()));
            }
        });
        return handlers;
    }

    private Map<String, AttributeHandler<OtherTrait>> otherTraitsHandlers() {
        Map<String, AttributeHandler<OtherTrait>> handlers = new HashMap<>();
        handlers.put("name", new AttributeHandler<OtherTrait>() {
            @Override
            public void handle(Node node, OtherTrait entity) {
                entity.setName(node.getTextContent());
            }
        });
        return handlers;
    }

    public void importPaths() throws ParserConfigurationException, IOException, SAXException {
        logger.info("Importing Paths");
        Map<String, AttributeHandler<Path>> handlers = new HashMap<>();
        handlers.put("name", new AttributeHandler<Path>() {
            @Override
            public void handle(Node node, Path entity) {
                entity.setName(node.getTextContent());
            }
        });
        Saver<Path> pathSaver = new Saver<Path>() {
            @Override
            public Path save(Path entity) {
                return manipulationDB.savePath(entity);
            }
        };
        logger.info("\tImporting Necromancy Paths");
        importFromResource(getClass().getResourceAsStream("/import/necromancy_paths.xml"), "necromancy_paths", "id", null,
                new Creator<Path>() {
                    @Override
                    public Path create() {
                        Path path = manipulationDB.newPath();
                        path.setType(Path.Type.Necromancy);
                        return path;
                    }
                },
                pathSaver,
                handlers);
        logger.info("\tImporting Thaumaturgy Paths");
        importFromResource(getClass().getResourceAsStream("/import/thama_paths.xml"), "thama_paths", "id", null,
                new Creator<Path>() {
                    @Override
                    public Path create() {
                        Path path = manipulationDB.newPath();
                        path.setType(Path.Type.Thaumaturgy);
                        return path;
                    }
                },
                pathSaver,
                handlers);
    }

    private Map<String, AttributeHandler<MeritOrFlaw>> meritOrFlawHandlers() {
        Map<String, AttributeHandler<MeritOrFlaw>> handlers = new HashMap<>();
        handlers.put("name", new AttributeHandler<MeritOrFlaw>() {
            @Override
            public void handle(Node node, MeritOrFlaw entity) {
                entity.setName(node.getTextContent());
            }
        });
        handlers.put("points", new AttributeHandler<MeritOrFlaw>() {
            @Override
            public void handle(Node node, MeritOrFlaw entity) {
                entity.setPoints(Integer.parseInt(node.getTextContent()));
            }
        });
        handlers.put("type", new AttributeHandler<MeritOrFlaw>() {
            @Override
            public void handle(Node node, MeritOrFlaw entity) {
                entity.setType(MeritOrFlaw.Type.findByChar(node.getTextContent().charAt(0)));
            }
        });
        return handlers;
    }

    private Map<String, AttributeHandler<Influence>> influenceHandlers() {
        Map<String, AttributeHandler<Influence>> handlers = new HashMap<>();
        handlers.put("name", new AttributeHandler<Influence>() {
            @Override
            public void handle(Node node, Influence entity) {
                entity.setName(node.getTextContent());
            }
        });
        return handlers;
    }

    private Map<String, AttributeHandler<Generation>> generationHandlers() {
        Map<String, AttributeHandler<Generation>> handlers = new HashMap<>();
        handlers.put("generation", new AttributeHandler<Generation>() {
            @Override
            public void handle(Node node, Generation entity) {
                entity.setGeneration(Integer.parseInt(node.getTextContent()));
            }
        });
        handlers.put("bloodpool", new AttributeHandler<Generation>() {
            @Override
            public void handle(Node node, Generation entity) {
                entity.setBloodPool(Integer.parseInt(node.getTextContent()));
            }
        });
        handlers.put("bloodpool_spend", new AttributeHandler<Generation>() {
            @Override
            public void handle(Node node, Generation entity) {
                entity.setSpendBlood(Integer.parseInt(node.getTextContent()));
            }
        });
        handlers.put("abilities_max", new AttributeHandler<Generation>() {
            @Override
            public void handle(Node node, Generation entity) {
                entity.setAbilitiesMax(Integer.parseInt(node.getTextContent()));
            }
        });
        handlers.put("disciplines_max", new AttributeHandler<Generation>() {
            @Override
            public void handle(Node node, Generation entity) {
                entity.setDisciplinesMax(Integer.parseInt(node.getTextContent()));
            }
        });
        handlers.put("traits_max", new AttributeHandler<Generation>() {
            @Override
            public void handle(Node node, Generation entity) {
                entity.setTraitsMax(Integer.parseInt(node.getTextContent()));
            }
        });
        handlers.put("willpower_start", new AttributeHandler<Generation>() {
            @Override
            public void handle(Node node, Generation entity) {
                entity.setWillpowerStart(Integer.parseInt(node.getTextContent()));
            }
        });
        handlers.put("willpower_max", new AttributeHandler<Generation>() {
            @Override
            public void handle(Node node, Generation entity) {
                entity.setWillpowerStart(Integer.parseInt(node.getTextContent()));
            }
        });
        handlers.put("bloodpool_human", new AttributeHandler<Generation>() {
            @Override
            public void handle(Node node, Generation entity) {
                entity.setHumanBlood(Integer.parseInt(node.getTextContent()));
            }
        });
        handlers.put("ghoul", new AttributeHandler<Generation>() {
            @Override
            public void handle(Node node, Generation entity) {
                entity.setGhoulLevel(Boolean.parseBoolean(node.getTextContent()));
            }
        });
        return handlers;
    }

    private Map<String, AttributeHandler<FightOrFlight>> fightOrFlightHandlers() {
        Map<String, AttributeHandler<FightOrFlight>> handlers = new HashMap<>();
        handlers.put("name", new AttributeHandler<FightOrFlight>() {
            @Override
            public void handle(Node node, FightOrFlight entity) {
                entity.setName(node.getTextContent());
            }
        });
        handlers.put("description", new AttributeHandler<FightOrFlight>() {
            @Override
            public void handle(Node node, FightOrFlight entity) {
                entity.setDescription(node.getTextContent());
            }
        });
        return handlers;
    }

    private Map<String, AttributeHandler<Discipline>> disciplineHandlers() {
        Map<String, AttributeHandler<Discipline>> handlers = new HashMap<>();
        handlers.put("name", new AttributeHandler<Discipline>() {
            @Override
            public void handle(Node node, Discipline entity) {
                entity.setName(node.getTextContent());
            }
        });
        handlers.put("retest_ability", new AttributeHandler<Discipline>() {
            @Override
            public void handle(Node node, Discipline entity) {
                if (node.getTextContent() != null && !node.getTextContent().isEmpty()) {
                    Ability ability = abilities.get(node.getTextContent());
                    entity.setRetestAbility(ability);
                }
            }
        });
        return handlers;
    }

    private Map<String, AttributeHandler<Clan>> clanHandlers() {
        Map<String, AttributeHandler<Clan>> handlers = new HashMap<>();
        handlers.put("name", new AttributeHandler<Clan>() {
            @Override
            public void handle(Node node, Clan entity) {
                entity.setName(node.getTextContent());
            }
        });
        handlers.put("base_income", new AttributeHandler<Clan>() {
            @Override
            public void handle(Node node, Clan entity) {
                entity.setBaseIncome(Integer.parseInt(node.getTextContent()));
            }
        });
        handlers.put("weaknesses", new AttributeHandler<Clan>() {
            @Override
            public void handle(Node node, Clan entity) {
                entity.setWeaknesses(node.getTextContent());
            }
        });
        return handlers;
    }

    private Map<String, AttributeHandler<CreateRule>> createRulesHandlers() {
        Map<String, AttributeHandler<CreateRule>> handlers = new HashMap<>();
        handlers.put("year_min", new AttributeHandler<CreateRule>() {
            @Override
            public void handle(Node node, CreateRule entity) {
                entity.setYearMin(Integer.parseInt(node.getTextContent()));
            }
        });
        handlers.put("year_max", new AttributeHandler<CreateRule>() {
            @Override
            public void handle(Node node, CreateRule entity) {
                entity.setYearMax(Integer.parseInt(node.getTextContent()));
            }
        });
        handlers.put("disciplines", new AttributeHandler<CreateRule>() {
            @Override
            public void handle(Node node, CreateRule entity) {
                entity.setDisciplines(Integer.parseInt(node.getTextContent()));
            }
        });
        handlers.put("attributes", new AttributeHandler<CreateRule>() {
            @Override
            public void handle(Node node, CreateRule entity) {
                entity.setAttributes(Integer.parseInt(node.getTextContent()));
            }
        });
        handlers.put("abilities", new AttributeHandler<CreateRule>() {
            @Override
            public void handle(Node node, CreateRule entity) {
                entity.setAbilities(Integer.parseInt(node.getTextContent()));
            }
        });
        return handlers;
    }

    private Map<String, AttributeHandler<Ability>> abilityHandlers() {
        Map<String, AttributeHandler<Ability>> handlers = new HashMap<>();
        handlers.put("name", new AttributeHandler<Ability>() {
            @Override
            public void handle(Node node, Ability entity) {
                entity.setName(node.getTextContent());
            }
        });
        handlers.put("base_monthlyincome", new AttributeHandler<Ability>() {
            @Override
            public void handle(Node node, Ability entity) {
                entity.setBaseMonthlyIncome(Integer.parseInt(node.getTextContent()));
            }
        });
        handlers.put("type", new AttributeHandler<Ability>() {
            @Override
            public void handle(Node node, Ability entity) {
                entity.setType(Ability.Type.findByChar(node.getTextContent().charAt(0)));
            }
        });
        return handlers;
    }

    private <T> void mapImportFromResource(InputStream input, String entityTagName, Mapper<T> mapper, Saver<T> saver) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
        Document doc = docBuilder.parse(input);

        // normalize text representation
        doc.getDocumentElement().normalize();

        NodeList nodes = doc.getElementsByTagName(entityTagName);
        for (int i = 0; i < nodes.getLength(); i++) {
            Element element = (Element)nodes.item(i);
            Map<String, String> entityMap = new HashMap<>();
            NodeList childNodes = element.getChildNodes();
            for (int j = 0; j < childNodes.getLength(); j++) {
                Node item = childNodes.item(j);
                entityMap.put(item.getNodeName(), item.getTextContent());
            }
            T entity = mapper.map(entityMap);
            if (entity != null) {
                saver.save(entity);
            }
        }
    }

    private <T> void importFromResource(InputStream input, String entityTagName, String idTagName,
                                        Map<String, T> entityCash, Creator<T> creator, Saver<T> saver,
                                        Map<String, AttributeHandler<T>> handlers) throws ParserConfigurationException, IOException, SAXException {

        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
        Document doc = docBuilder.parse(input);

        // normalize text representation
        doc.getDocumentElement().normalize();

        NodeList nodes = doc.getElementsByTagName(entityTagName);
        for (int i = 0; i < nodes.getLength(); i++) {
            Element element = (Element)nodes.item(i);
            String key = null;
            T theObject = creator.create();
            NodeList childNodes = element.getChildNodes();
            for (int j = 0; j < childNodes.getLength(); j++) {
                Node item = childNodes.item(j);
                if (key == null && idTagName.equals(item.getNodeName())) {
                    key = item.getTextContent();
                }
                AttributeHandler<T> handler = handlers.get(item.getNodeName());
                if (handler != null) {
                    handler.handle(item, theObject);
                }

            }
            if (key != null && entityCash != null) {
                entityCash.put(key, saver.save(theObject));
            } else {
                saver.save(theObject);
            }
        }
    }

    static interface Mapper<T> {
        T map(Map<String, String> entity);
    }

    static interface Creator<T> {
        T create();
    }

    static interface Saver<T> {
        T save(T entity);
    }

    static interface AttributeHandler<T> {
        void handle(Node node, T entity);
    }
}
