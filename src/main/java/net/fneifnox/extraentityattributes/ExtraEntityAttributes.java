package net.fneifnox.extraentityattributes;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static net.fneifnox.extraentityattributes.attributes.maxJumps.JumpAmountModifier.modifyJumpAmount;

public class ExtraEntityAttributes implements ModInitializer, ClientModInitializer {
	public static final String MOD_ID = "extra-entity-attributes";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	// Max Hunger
	public static boolean hasBeenCalled = false;

	@Override
	public void onInitialize() {

		// Max Hunger
		ServerPlayerEvents.COPY_FROM.register((oldPlayer, newPlayer, alive) -> {
			if (!alive) {
				// To set the hunger from a respawned player to the max amount
				newPlayer.getHungerManager().setFoodLevel((int) newPlayer.getAttributeValue(ExtraEntityAttributes.MAX_HUNGER));
			}
		});
	}

	@Override
	public void onInitializeClient() {

		System.out.println("TEST 1");
		// Max Jumps
		ClientTickEvents.START_CLIENT_TICK.register(client -> {
			if (client.player == null) return;
			System.out.println("TEST 2");
			modifyJumpAmount((int) client.player.getAttributeValue(ExtraEntityAttributes.MAX_JUMPS));
		});
	}

	public static final RegistryEntry<EntityAttribute> VILLAGER_DISCOUNT = registerAttribute("villager_discount", 0, -4, 1);
	public static final RegistryEntry<EntityAttribute> MAX_HUNGER = registerAttribute("max_hunger", 20, 1, 1024);
	public static final RegistryEntry<EntityAttribute> FOOD_NUTRITION_MULTIPLIER = registerAttribute("food_nutrition_multiplier", 1, 0.1, 64);
	public static final RegistryEntry<EntityAttribute> FOOD_SATURATION_MULTIPLIER = registerAttribute("food_saturation_multiplier", 1, 0.1, 64);
	public static final RegistryEntry<EntityAttribute> EATING_SPEED = registerAttribute("eating_speed", 1, 1, 1024);
	public static final RegistryEntry<EntityAttribute> DRINKING_SPEED = registerAttribute("drinking_speed", 1, 1, 1024);
	public static final RegistryEntry<EntityAttribute> STATUS_EFFECT_DURATION = registerAttribute("status_effect_duration", 1, 0.01, 64);
	public static final RegistryEntry<EntityAttribute> LIFESTEAL = registerAttribute("lifesteal", 0, -64, 64);
	public static final RegistryEntry<EntityAttribute> HUNGERSTEAL = registerAttribute("hungersteal", 0, -64, 64);
	public static final RegistryEntry<EntityAttribute> DODGE_CHANCE = registerAttribute("dodge_chance", 0, 0, 1);
	public static final RegistryEntry<EntityAttribute> SPRINTING_FOOD = registerAttribute("sprinting_food", 6, 0, 1024);
	public static final RegistryEntry<EntityAttribute> CROSSBOW_PULL_TIME_MULTIPLIER = registerAttribute("crossbow_pull_time_multiplier", 1, 0, 64);
	public static final RegistryEntry<EntityAttribute> FISHING_DURATION_MULTIPLIER = registerAttribute("fishing_duration_multiplier", 1, 0, 64);
	public static final RegistryEntry<EntityAttribute> ADDITIONAL_FISHING_LUCK = registerAttribute("additional_fishing_luck", 0, -100, 100);
	public static final RegistryEntry<EntityAttribute> SPRINTING_SPEED = registerAttribute("sprinting_speed", 0.13, 0.01, 1024);
	public static final RegistryEntry<EntityAttribute> CLIMBING_SPEED = registerAttribute("climbing_speed", 0.2, 0.01, 128);
	public static final RegistryEntry<EntityAttribute> CREATIVE_FLYING_SPEED = registerAttribute("creative_flying_speed", 0.05, 0.001, 128);
	public static final RegistryEntry<EntityAttribute> MOUNT_SPEED_MULTIPLIER = registerAttribute("mount_speed_multiplier", 1, 0, 64);
	public static final RegistryEntry<EntityAttribute> MAX_JUMPS = registerAttribute("max_jumps", 1, 1, 1024);
	public static final RegistryEntry<EntityAttribute> SLIPPERINESS = registerAttribute("slipperiness", 1, 0, 1);
	public static final RegistryEntry<EntityAttribute> NAME_TAG_VISIBILITY_RANGE = registerAttribute("name_tag_visibility_range", 64, 0, 64);

	public static RegistryEntry<EntityAttribute> registerAttribute(final String name, double base, double min, double max) {
		EntityAttribute attribute = new ClampedEntityAttribute("attribute." + MOD_ID + '.' + name, base, min, max).setTracked(true);
		return Registry.registerReference(Registries.ATTRIBUTE, Identifier.of(MOD_ID, name), attribute);
	}
}