package com.christofmeg.justenoughbreeding.config;

import com.christofmeg.justenoughbreeding.CommonConstants;
import com.christofmeg.justenoughbreeding.config.integration.*;
import com.christofmeg.justenoughbreeding.utils.ForgeUtils;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JEBConfig {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    @SuppressWarnings("unused")
    public static final General GENERAL = new General(BUILDER);

    private static final String MOD = "minecraft";

    public static class General {
        private static final String TROPICAL_FISH_BUCKET = "minecraft:tropical_fish_bucket";
        private static final String FLOWERS = "#minecraft:flowers";
        private static final String COD_SALMON = "minecraft:cod, minecraft:salmon";
// 1.20        private static final String CACTUS = "minecraft:cactus";
        private static final String SEEDS = "minecraft:wheat_seeds, minecraft:pumpkin_seeds, minecraft:melon_seeds, minecraft:beetroot_seeds, minecraft:torchflower_seeds, minecraft:pitcher_pod";
        private static final String WHEAT = "minecraft:wheat";
        private static final String GOLDEN_APPLE_CARROT = "minecraft:golden_apple, minecraft:enchanted_golden_apple, minecraft:golden_carrot";
        private static final String BERRIES = "minecraft:sweet_berries, minecraft:glow_berries";
        private static final String SLIME_BALL = "minecraft:slime_ball";
        private static final String CRIMSON_FUNGUS = "minecraft:crimson_fungus";
        private static final String HAY_BLOCK = "minecraft:hay_block";
        private static final String BAMBOO = "minecraft:bamboo";
        private static final String VEGETABLES = "minecraft:carrot, minecraft:potato, minecraft:beetroot";
        private static final String DANDELION_CARROTS = "minecraft:dandelion, minecraft:carrot, minecraft:golden_carrot";
// 1.20        private static final String TORCHFLOWERS_SEEDS = "minecraft:torchflower_seeds";
        private static final String WARPED_FUNGUS = "minecraft:warped_fungus";
        private static final String SEAGRASS = "minecraft:seagrass";
        private static final String MEAT = ForgeUtils.getEdibleMeatItemNames(true); //TODO Foodproperties.isMeat() to other mods

        private static final List<String> animalNames = new ArrayList<>();
        private static final Map<String, String> ingredients = new HashMap<>();
        private static final Map<String, Boolean> needsToBeTamed = new HashMap<>();
        private static final Map<String, String> resultEggs = new HashMap<>();
        private static final Map<String, Integer> eggsAmountMin = new HashMap<>();
        private static final Map<String, Integer> eggsAmountMax = new HashMap<>();

        public General(ForgeConfigSpec.Builder builder) {
            builder.comment("Breeding Recipes Configuration").push("breeding");

            builder.push("vanilla");

            addAnimal("axolotl", TROPICAL_FISH_BUCKET);
            addAnimal("bee", FLOWERS);
            addAnimalTamed("cat", COD_SALMON);
// 1.20            addAnimal("camel", CACTUS);
            addAnimal("chicken", SEEDS);
            addAnimal("cow", WHEAT);
            addAnimalTamed("donkey", GOLDEN_APPLE_CARROT);
            addAnimal("fox", BERRIES);
            addEggLayingAnimal("frog", SLIME_BALL, "minecraft:frogspawn", 1, 1);
            addAnimal("goat", WHEAT);
            addAnimal("hoglin", CRIMSON_FUNGUS);
            addAnimalTamed("horse", GOLDEN_APPLE_CARROT);
            addAnimal("llama", HAY_BLOCK);
            addAnimal("mooshroom", WHEAT);
            addAnimal("ocelot", COD_SALMON);
            addAnimal("panda", BAMBOO);
            addAnimal("pig", VEGETABLES);
            addAnimal("rabbit", DANDELION_CARROTS);
            addAnimal("sheep", WHEAT);
// 1.20            addAnimal("sniffer", TORCHFLOWERS_SEEDS);
            addAnimal("strider", WARPED_FUNGUS);
            addAnimal("trader_llama", HAY_BLOCK);
            addEggLayingAnimal("turtle", SEAGRASS, "minecraft:turtle_egg", 1, 4);
            addAnimalTamed("wolf", MEAT);

            for (String animal : animalNames) {
                ForgeConfigSpec.ConfigValue<String> animalSpawnEgg = builder.define(animal + "SpawnEgg", MOD + ":" + animal + "_spawn_egg");
                ForgeConfigSpec.ConfigValue<String> animalIngredients = builder.push(animal)
                        .comment("Ingredients required for " + animal + " breeding")
                        .define(animal + "Ingredients", ingredients.get(animal));
                builder.pop();
                CommonConstants.ingredientConfigs.put(MOD + "_" + animal, animalIngredients);
                CommonConstants.spawnEggConfigs.put(MOD + "_" + animal, animalSpawnEgg);
                if(needsToBeTamed.get(animal) != null) {
                    CommonConstants.animalTamedConfigs.put(MOD + "_" + animal, true);
                }
                if(resultEggs.get(animal) != null && eggsAmountMin.get(animal) != null && eggsAmountMax.get(animal) != null) {
                    ForgeConfigSpec.ConfigValue<String> animalEggResult = builder
                            .comment("Egg that " + animal + " lays after breeding")
                            .define(animal + "eggResult", resultEggs.get(animal));
                    ForgeConfigSpec.ConfigValue<Integer> animalMinEggAmount = builder
                            .comment("Min amount of eggs that " + animal + " lays after breeding")
                            .defineInRange(animal + "EggMinAmount", eggsAmountMin.get(animal), 1, 64);
                    ForgeConfigSpec.ConfigValue<Integer> animalMaxEggAmount = builder
                            .comment("Max amount of eggs that " + animal + " lays after breeding")
                            .defineInRange(animal + "EggMaxAmount", eggsAmountMax.get(animal), 1, 64);
                    CommonConstants.eggResultConfigs.put(MOD + "_" + animal, animalEggResult);
                    CommonConstants.eggMinAmountConfigs.put(MOD + "_" + animal, animalMinEggAmount);
                    CommonConstants.eggMaxAmountConfigs.put(MOD + "_" + animal, animalMaxEggAmount);
                }
            }

            builder.pop();

            if (ModList.get().isLoaded("alexsmobs")) {
                @SuppressWarnings("unused")
                final AlexsMobsIntegration.General CONFIG = new AlexsMobsIntegration.General(BUILDER);
            }
            if (ModList.get().isLoaded("snuffles")) {
                @SuppressWarnings("unused")
                final SnufflesIntegration.General CONFIG = new SnufflesIntegration.General(BUILDER);
            }
            if (ModList.get().isLoaded("snowpig")) {
                @SuppressWarnings("unused")
                final SnowPigIntegration.General CONFIG = new SnowPigIntegration.General(BUILDER);
            }
            if (ModList.get().isLoaded("aqcaracal")) {
                @SuppressWarnings("unused")
                final AqcaracalIntegration.General CONFIG = new AqcaracalIntegration.General(BUILDER);
            }
            if (ModList.get().isLoaded("theducksmod")) {
                @SuppressWarnings("unused")
                final TheDucksModIntegration.General CONFIG = new TheDucksModIntegration.General(BUILDER);
            }
            if (ModList.get().isLoaded("fennecfox")) {
                @SuppressWarnings("unused")
                final FennecFoxIntegration.General CONFIG = new FennecFoxIntegration.General(BUILDER);
            }
            if (ModList.get().isLoaded("apple_cows")) {
                @SuppressWarnings("unused")
                final AppleCowsIntegration.General CONFIG = new AppleCowsIntegration.General(BUILDER);
            }
            if (ModList.get().isLoaded("ydms_redpanda")) {
                @SuppressWarnings("unused")
                final RedPandaIntegration.General CONFIG = new RedPandaIntegration.General(BUILDER);
            }
            if (ModList.get().isLoaded("frozenup")) {
                @SuppressWarnings("unused")
                final FrozenUpIntegration.General CONFIG = new FrozenUpIntegration.General(BUILDER);
            }
            if (ModList.get().isLoaded("glare")) {
                @SuppressWarnings("unused")
                final GlareIntegration.General CONFIG = new GlareIntegration.General(BUILDER);
            }
            if (ModList.get().isLoaded("greekfantasy")) {
                @SuppressWarnings("unused")
                final GreekFantasyIntegration.General CONFIG = new GreekFantasyIntegration.General(BUILDER);
            }
            if (ModList.get().isLoaded("sophisticated_wolves")) {
                @SuppressWarnings("unused")
                final SophisticatedWolvesIntegration.General CONFIG = new SophisticatedWolvesIntegration.General(BUILDER);
            }
            if (ModList.get().isLoaded("lilwings")) {
                @SuppressWarnings("unused")
                final LilWingsIntegration.General CONFIG = new LilWingsIntegration.General(BUILDER);
            }
            if (ModList.get().isLoaded("steves_vanilla")) {
                @SuppressWarnings("unused")
                final StevesVanillaIntegration.General CONFIG = new StevesVanillaIntegration.General(BUILDER);
            }
            if (ModList.get().isLoaded("duckling")) {
                @SuppressWarnings("unused")
                final DucklingIntegration.General CONFIG = new DucklingIntegration.General(BUILDER);
            }
            if (ModList.get().isLoaded("ulterlands")) {
                @SuppressWarnings("unused")
                final UlterlandsIntegration.General CONFIG = new UlterlandsIntegration.General(BUILDER);
            }
            if (ModList.get().isLoaded("twilightforest")) {
                @SuppressWarnings("unused")
                final TwilightForestIntegration.General CONFIG = new TwilightForestIntegration.General(BUILDER);
            }
/*
            if (ModList.get().isLoaded("chococraft")) {
                @SuppressWarnings("unused")
                final ChocoCraftIntegration.General CONFIG = new ChocoCraftIntegration.General(BUILDER);
            }
*/
            if (ModList.get().isLoaded("waddles")) {
                @SuppressWarnings("unused")
                final WaddlesIntegration.General CONFIG = new WaddlesIntegration.General(BUILDER);
            }
            if (ModList.get().isLoaded("aquaculture")) {
                @SuppressWarnings("unused")
                final AquacultureIntegration.General CONFIG = new AquacultureIntegration.General(BUILDER);
            }
            if (ModList.get().isLoaded("ecologics")) {
                @SuppressWarnings("unused")
                final EcologicsIntegration.General CONFIG = new EcologicsIntegration.General(BUILDER);
            }
            if (ModList.get().isLoaded("ostrich")) {
                @SuppressWarnings("unused")
                final OstrichIntegration.General CONFIG = new OstrichIntegration.General(BUILDER);
            }
            if (ModList.get().isLoaded("marineiguana")) {
                @SuppressWarnings("unused")
                final MarineIguanaIntegration.General CONFIG = new MarineIguanaIntegration.General(BUILDER);
            }
            if (ModList.get().isLoaded("blue_skies")) {
                @SuppressWarnings("unused")
                final BlueSkiesIntegration.General CONFIG = new BlueSkiesIntegration.General(BUILDER);
            }
            if (ModList.get().isLoaded("naturalist")) {
                @SuppressWarnings("unused")
                final NaturalistIntegration.General CONFIG = new NaturalistIntegration.General(BUILDER);
            }

        }
        private void addAnimal(String name, String ingredient) {
            animalNames.add(name);
            ingredients.put(name, ingredient);
        }

        private void addAnimalTamed(String name, String ingredient) {
            addAnimal(name, ingredient);
            needsToBeTamed.put(name, true);
        }

        private void addEggLayingAnimal(String name, String ingredient, String resultEgg, int eggAmountMin, int eggAmountMax) {
            addAnimal(name, ingredient);
            resultEggs.put(name, resultEgg);
            eggsAmountMin.put(name, eggAmountMin);
            eggsAmountMax.put(name, eggAmountMax);
        }
    }

    public static final ForgeConfigSpec spec = BUILDER.build();
}