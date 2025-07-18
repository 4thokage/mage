package mage.abilities.effects;

import mage.abilities.MageSingleton;
import mage.abilities.Mode;
import mage.constants.EffectType;
import mage.constants.Outcome;
import mage.target.targetpointer.FirstTargetPointer;
import mage.target.targetpointer.TargetPointer;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public abstract class EffectImpl implements Effect {

    protected UUID id;
    protected Outcome outcome;
    protected EffectType effectType;

    // read related docs about static and dynamic targets in ContinuousEffectImpl.affectedObjectsSet
    // warning, do not change it directly, use setTargetPointer instead
    private TargetPointer targetPointer = new FirstTargetPointer();

    protected String staticText = "";
    protected Map<String, Object> values;
    protected String concatPrefix = ""; // combines multiple effects in text rule

    public EffectImpl(Outcome outcome) {
        this.id = UUID.randomUUID();
        this.outcome = outcome;

        initNewTargetPointer();
    }

    protected EffectImpl(final EffectImpl effect) {
        this.id = effect.id;
        this.outcome = effect.outcome;
        this.staticText = effect.staticText;
        this.effectType = effect.effectType;
        this.targetPointer = effect.targetPointer.copy();
        this.concatPrefix = effect.concatPrefix;
        if (effect.values != null) {
            values = new HashMap<>();
            Map<String, Object> map = effect.values;
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                values.put(entry.getKey(), entry.getValue());
            }
        }
    }

    /**
     * Init target pointer by default (see TargetPointer for details)
     */
    abstract public void initNewTargetPointer();

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public String getText(Mode mode) {
        return staticText;
    }

    @Override
    public Effect setText(String staticText) {
        this.staticText = staticText;
        return this;
    }

    @Override
    public Outcome getOutcome() {
        return outcome;
    }

    @Override
    public void setOutcome(Outcome outcome) {
        this.outcome = outcome;
    }

    @Override
    public EffectType getEffectType() {
        return effectType;
    }

    @Override
    public Effect setTargetPointer(TargetPointer targetPointer) {
        if (targetPointer == null) {
            // first target pointer is default
            throw new IllegalArgumentException("Wrong code usage: target pointer can't be set to null: " + this);
        }
        targetPointer.setTargetDescription(this.targetPointer.getTargetDescription()); // copies the null if not set
        this.targetPointer = targetPointer;
        initNewTargetPointer();
        return this;
    }

    @Override
    public TargetPointer getTargetPointer() {
        return this.targetPointer;
    }

    @Override
    public Effect withTargetDescription(String target) {
        this.targetPointer.setTargetDescription(target);
        return this;
    }

    @Override
    public void newId() {
        if (!(this instanceof MageSingleton)) {
            this.id = UUID.randomUUID();
        }
    }

    @Override
    public void setValue(String key, Object value) {
        synchronized (this) {
            if (values == null) {
                values = new HashMap<>();
            }
        }
        values.put(key, value);
    }

    @Override
    public Object getValue(String key) {
        if (values == null) { // no value was set
            return null;
        }
        return values.get(key);
    }

    @Override
    public Effect concatBy(String concatPrefix) {
        this.concatPrefix = concatPrefix;
        return this;
    }

    @Override
    public String getConcatPrefix() {
        return this.concatPrefix;
    }

}
