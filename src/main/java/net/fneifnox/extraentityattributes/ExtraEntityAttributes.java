package net.fneifnox.extraentityattributes;

import net.fabricmc.api.ModInitializer;

import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExtraEntityAttributes implements ModInitializer {
	public static final String MOD_ID = "extra-entity-attributes";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {

	}

	public static final RegistryEntry<EntityAttribute> VILLAGER_DISCOUNT = registerAttribute("villager_discount", 0.0, -4, 1);
	public static final RegistryEntry<EntityAttribute> MAX_HUNGER = registerAttribute("max_hunger", 20, 1, 1024);
	public static final RegistryEntry<EntityAttribute> FOOD_NUTRITION_MULTIPLIER = registerAttribute("food_nutrition_multiplier", 1, 0.1, 64);
	public static final RegistryEntry<EntityAttribute> FOOD_SATURATION_MULTIPLIER = registerAttribute("food_saturation_multiplier", 1, 0.1, 64);
	public static final RegistryEntry<EntityAttribute> EATING_SPEED = registerAttribute("eating_speed", 1.0, 1.0, 1024);
	public static final RegistryEntry<EntityAttribute> DRINKING_SPEED = registerAttribute("drinking_speed", 1.0, 1.0, 1024);
	public static final RegistryEntry<EntityAttribute> STATUS_EFFECT_DURATION = registerAttribute("status_effect_duration", 1, 0.01, 64);
	public static final RegistryEntry<EntityAttribute> SPRINTING_SPEED = registerAttribute("sprinting_speed", 0.13, 0.01, 1024);
	public static final RegistryEntry<EntityAttribute> CLIMBING_SPEED = registerAttribute("climbing_speed", 0.2, 0.01, 128);
	public static final RegistryEntry<EntityAttribute> CREATIVE_FLYING_SPEED = registerAttribute("creative_flying_speed", 0.05, 0.001, 128);
	public static final RegistryEntry<EntityAttribute> SLIPPERINESS = registerAttribute("slipperiness", 1.0, 0.0, 1.0);
	public static final RegistryEntry<EntityAttribute> NAME_TAG_VISIBILITY_RANGE = registerAttribute("name_tag_visibility_range", 64, 0, 64);


	public static RegistryEntry<EntityAttribute> registerAttribute(final String name, double base, double min, double max) {
		EntityAttribute attribute = new ClampedEntityAttribute("attribute." + MOD_ID + '.' + name, base, min, max).setTracked(true);
		return Registry.registerReference(Registries.ATTRIBUTE, Identifier.of(MOD_ID, name), attribute);
	}
}