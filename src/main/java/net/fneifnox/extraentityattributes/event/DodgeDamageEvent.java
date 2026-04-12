package net.fneifnox.extraentityattributes.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;

public interface DodgeDamageEvent {
    Event<DodgeDamageEvent> EVENT = EventFactory.createArrayBacked(
            DodgeDamageEvent.class,
            listeners -> (entity, source, amount) -> {
                for (DodgeDamageEvent listener : listeners) {
                    listener.onDodge(entity, source, amount);
                }
            }
    );

    void onDodge(LivingEntity attackedLivingEntity, DamageSource source, float amount);
}
