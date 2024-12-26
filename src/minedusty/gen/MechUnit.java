package minedusty.gen;

import java.util.*;
import static mindustry.logic.GlobalVars.*;
import mindustry.entities.units.*;
import mindustry.entities.EntityCollisions.*;
import arc.graphics.*;
import mindustry.ai.*;
import arc.struct.*;
import mindustry.ai.types.*;
import arc.struct.Queue;
import arc.util.io.*;
import arc.func.*;
import mindustry.logic.*;
import mindustry.world.blocks.payloads.*;
import mindustry.*;
import mindustry.async.PhysicsProcess.*;
import mindustry.content.*;
import mindustry.world.blocks.environment.*;
import arc.math.geom.QuadTree.*;
import mindustry.graphics.*;
import mindustry.ui.*;
import mindustry.world.*;
import arc.util.*;
import mindustry.ctype.*;
import mindustry.core.*;
import arc.math.*;
import mindustry.world.blocks.ConstructBlock.*;
import static mindustry.Vars.*;
import arc.scene.ui.layout.*;
import arc.*;
import mindustry.type.*;
import mindustry.entities.*;
import mindustry.game.*;
import java.nio.*;
import mindustry.input.*;
import arc.graphics.g2d.*;
import mindustry.gen.*;
import mindustry.game.EventType.*;
import mindustry.entities.abilities.*;
import mindustry.world.blocks.storage.CoreBlock.*;
import mindustry.world.blocks.*;
import ent.anno.Annotations.*;
import arc.math.geom.*;
import arc.util.pooling.*;

import arc.func.Cons;
import arc.graphics.Color;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import arc.math.geom.Position;
import arc.math.geom.QuadTree;
import arc.math.geom.Rect;
import arc.math.geom.Vec2;
import arc.scene.ui.layout.Table;
import arc.struct.Bits;
import arc.struct.Seq;
import arc.util.Time;
import arc.util.io.Reads;
import arc.util.io.Writes;
import ent.anno.Annotations;
import java.nio.FloatBuffer;
import mindustry.Vars;
import mindustry.ai.types.CommandAI;
import mindustry.ctype.Content;
import mindustry.ctype.ContentType;
import mindustry.ctype.UnlockableContent;
import mindustry.entities.EntityCollisions;
import mindustry.entities.units.BuildPlan;
import mindustry.entities.units.StatusEntry;
import mindustry.entities.units.UnitController;
import mindustry.game.Team;
import mindustry.gen.Boundedc;
import mindustry.gen.Builderc;
import mindustry.gen.Building;
import mindustry.gen.Drawc;
import mindustry.gen.ElevationMovec;
import mindustry.gen.Entityc;
import mindustry.gen.Flyingc;
import mindustry.gen.Groups;
import mindustry.gen.Healthc;
import mindustry.gen.Hitboxc;
import mindustry.gen.Itemsc;
import mindustry.gen.Mechc;
import mindustry.gen.Minerc;
import mindustry.gen.Physicsc;
import mindustry.gen.Player;
import mindustry.gen.Posc;
import mindustry.gen.Rotc;
import mindustry.gen.Shieldc;
import mindustry.gen.Statusc;
import mindustry.gen.Syncc;
import mindustry.gen.Teamc;
import mindustry.gen.Unit;
import mindustry.gen.Unitc;
import mindustry.gen.Velc;
import mindustry.gen.Weaponsc;
import mindustry.logic.LAccess;
import mindustry.type.Item;
import mindustry.type.StatusEffect;
import mindustry.type.UnitType;
import mindustry.world.Block;
import mindustry.world.Tile;
import mindustry.world.blocks.environment.Floor;
import mindustry.world.blocks.storage.CoreBlock;

@SuppressWarnings({"all", "unchecked", "deprecation"})
public class MechUnit extends Unit implements Boundedc, Builderc, Drawc, ElevationMovec, Entityc, Flyingc, Healthc, Hitboxc, Itemsc, Mechc, Minerc, Physicsc, Posc, Rotc, Shieldc, Statusc, Syncc, Teamc, Unitc, Velc, Weaponsc {
    public static final float hitDuration = 9.0F;

    private static final Vec2 tmp1 = new Vec2();

    private static final Vec2 tmp2 = new Vec2();

    public static final float warpDst = 30.0F;

    private transient boolean added;

    private transient Bits applied = new Bits(content.getBy(ContentType.status).size);

    @Annotations.SyncField(false)
    @Annotations.SyncLocal
    public float baseRotation;

    private transient float baseRotation_LAST_;

    private transient float baseRotation_TARGET_;

    private transient float buildCounter;

    private UnitController controller;

    @Annotations.ReadOnly
    protected transient boolean isRotate;

    private transient BuildPlan lastActive;

    private transient int lastSize;

    private transient float resupplyTime = Mathf.random(10.0F);

    private transient float rotation_LAST_;

    private transient float rotation_TARGET_;

    private Seq<StatusEntry> statuses = new Seq<>();

    public transient float walkExtension;

    public transient float walkTime;

    private transient boolean walked;

    private transient boolean wasFlying;

    private transient boolean wasHealed;

    private transient boolean wasPlayer;

    private transient float x_LAST_;

    private transient float x_TARGET_;

    private transient float y_LAST_;

    private transient float y_TARGET_;

    protected MechUnit() {
    }

    @Override
    public <T extends Entityc> T self() {
        return (T)this;
    }

    @Override
    public <T> T as() {
        return (T)this;
    }

    @Override
    public Color statusColor() {
        if (statuses.size == 0) {
            return Tmp.c1.set(Color.white);
        }
        float r = 1.0F;
        float g = 1.0F;
        float b = 1.0F;
        float total = 0.0F;
        for (StatusEntry entry : statuses) {
            float intensity = entry.time < 10.0F ? entry.time / 10.0F : 1.0F;
            r += entry.effect.color.r * intensity;
            g += entry.effect.color.g * intensity;
            b += entry.effect.color.b * intensity;
            total += intensity;
        }
        float count = statuses.size + total;
        return Tmp.c1.set(r / count, g / count, b / count, 1.0F);
    }

    @Override
    public TextureRegion icon() {
        return type.fullIcon;
    }

    @Override
    public Bits statusBits() {
        return applied;
    }

    @Override
    public boolean acceptsItem(Item item) {
        return !hasItem() || item == stack.item && stack.amount + 1 <= itemCapacity();
    }

    @Override
    public boolean activelyBuilding() {
        if (isBuilding()) {
            var plan = buildPlan();
            if (!state.isEditor() && plan != null && !within(plan, state.rules.infiniteResources ? Float.MAX_VALUE : type.buildRange)) {
                return false;
            }
        }
        return isBuilding() && updateBuilding;
    }

    @Override
    public boolean canBuild() {
        return type.buildSpeed > 0 && buildSpeedMultiplier > 0;
    }

    @Override
    public boolean canDrown() {
        return isGrounded() && !hovering && type.canDrown;
    }

    @Override
    public boolean canLand() {
        return !onSolid() && Units.count(x, y, physicSize(), (f)->f != this && f.isGrounded()) == 0;
    }

    @Override
    public boolean canMine() {
        return type.mineSpeed > 0 && type.mineTier >= 0;
    }

    @Override
    public boolean canMine(Item item) {
        if (item == null) return false;
        return type.mineTier >= item.hardness;
    }

    @Override
    public boolean canPass(int tileX, int tileY) {
        SolidPred s = solidity();
        return s == null || !s.solid(tileX, tileY);
    }

    @Override
    public boolean canPassOn() {
        return canPass(tileX(), tileY());
    }

    @Override
    public boolean canShoot() {
        return !disarmed && !(type.canBoost && isFlying());
    }

    @Override
    public boolean cheating() {
        return team.rules().cheat;
    }

    @Override
    public boolean checkTarget(boolean targetAir, boolean targetGround) {
        return (isGrounded() && targetGround) || (isFlying() && targetAir);
    }

    @Override
    public boolean collides(Hitboxc other) {
        return hittable();
    }

    @Override
    public boolean damaged() {
        return health < maxHealth - 0.001F;
    }

    @Override
    public boolean displayable() {
        return type.hoverable;
    }

    @Override
    public boolean emitWalkSound() {
        return true;
    }

    @Override
    public boolean hasEffect(StatusEffect effect) {
        return applied.get(effect.id);
    }

    @Override
    public boolean hasItem() {
        return stack.amount > 0;
    }

    @Override
    public boolean hasWeapons() {
        return type.hasWeapons();
    }

    @Override
    public boolean hittable() {
        return type.hittable(this);
    }

    @Override
    public boolean inFogTo(Team viewer) {
        if (this.team == viewer || !state.rules.fog) return false;
        if (hitSize <= 16.0F) {
            return !fogControl.isVisible(viewer, x, y);
        } else {
            float trns = hitSize / 2.0F;
            for (var p : Geometry.d8) {
                if (fogControl.isVisible(viewer, x + p.x * trns, y + p.y * trns)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public boolean inRange(Position other) {
        return within(other, type.range);
    }

    @Override
    public boolean isAI() {
        return controller instanceof AIController;
    }

    @Override
    public boolean isAdded() {
        return added;
    }

    @Override
    public boolean isBoss() {
        return hasEffect(StatusEffects.boss);
    }

    @Override
    public boolean isBuilding() {
        return plans.size != 0;
    }

    @Override
    public boolean isCommandable() {
        return controller instanceof CommandAI;
    }

    @Override
    public boolean isEnemy() {
        return type.isEnemy;
    }

    @Override
    public boolean isFlying() {
        return elevation >= 0.09F;
    }

    @Override
    public boolean isGrounded() {
        return elevation < 0.001F;
    }

    @Override
    public boolean isImmune(StatusEffect effect) {
        return type.immunities.contains(effect);
    }

    @Override
    public boolean isLocal() {
        return ((Object)this) == player || ((Object)this) instanceof Unitc u && u.controller() == player;
    }

    @Override
    public boolean isNull() {
        return false;
    }

    @Override
    public boolean isPathImpassable(int tileX, int tileY) {
        return !type.flying && world.tiles.in(tileX, tileY) && type.pathCost.getCost(team.id, pathfinder.get(tileX, tileY)) == -1;
    }

    @Override
    public boolean isPlayer() {
        return controller instanceof Player;
    }

    @Override
    public boolean isRemote() {
        return ((Object)this) instanceof Unitc u && u.isPlayer() && !isLocal();
    }

    @Override
    public boolean isRotate() {
        return isRotate;
    }

    @Override
    public boolean isSyncHidden(Player player) {
        return !isShooting() && inFogTo(player.team());
    }

    @Override
    public boolean isValid() {
        return !dead && isAdded();
    }

    @Override
    public boolean mining() {
        return mineTile != null && !this.activelyBuilding();
    }

    @Override
    public boolean moving() {
        return !vel.isZero(0.01F);
    }

    @Override
    public boolean offloadImmediately() {
        return this.isPlayer();
    }

    @Override
    public boolean onSolid() {
        Tile tile = tileOn();
        return tile == null || tile.solid();
    }

    @Override
    public boolean serialize() {
        return true;
    }

    @Override
    public boolean shouldSkip(BuildPlan plan, Building core) {
        if (state.rules.infiniteResources || team.rules().infiniteResources || plan.breaking || core == null || plan.isRotation(team) || (isBuilding() && !within(plans.last(), type.buildRange))) return false;
        return (plan.stuck && !core.items.has(plan.block.requirements)) || (Structs.contains(plan.block.requirements, (i)->!core.items.has(i.item, Math.min(i.amount, 15)) && Mathf.round(i.amount * state.rules.buildCostMultiplier) > 0) && !plan.initialized);
    }

    @Override
    public boolean targetable(Team targeter) {
        return type.targetable(this, targeter);
    }

    @Override
    public boolean validMine(Tile tile) {
        return validMine(tile, true);
    }

    @Override
    public boolean validMine(Tile tile, boolean checkDst) {
        if (tile == null) return false;
        if (checkDst && !within(tile.worldx(), tile.worldy(), type.mineRange)) {
            return false;
        }
        return getMineResult(tile) != null;
    }

    @Override
    public double sense(Content content) {
        if (content == stack().item) return stack().amount;
        return Float.NaN;
    }

    @Override
    public double sense(LAccess sensor) {
        return switch (sensor) {
        case totalItems ->stack().amount;
        case itemCapacity ->type.itemCapacity;
        case rotation ->rotation;
        case health ->health;
        case shield ->shield;
        case maxHealth ->maxHealth;
        case ammo ->!state.rules.unitAmmo ? type.ammoCapacity : ammo;
        case ammoCapacity ->type.ammoCapacity;
        case x ->World.conv(x);
        case y ->World.conv(y);
        case dead ->dead || !isAdded() ? 1 : 0;
        case team ->team.id;
        case shooting ->isShooting() ? 1 : 0;
        case boosting ->type.canBoost && isFlying() ? 1 : 0;
        case range ->range() / tilesize;
        case shootX ->World.conv(aimX());
        case shootY ->World.conv(aimY());
        case mining ->mining() ? 1 : 0;
        case mineX ->mining() ? mineTile.x : -1;
        case mineY ->mining() ? mineTile.y : -1;
        case flag ->flag;
        case speed ->type.speed * 60.0F / tilesize;
        case controlled ->!isValid() ? 0 : controller instanceof LogicAI ? ctrlProcessor : controller instanceof Player ? ctrlPlayer : controller instanceof CommandAI command && command.hasCommand() ? ctrlCommand : 0;
        case payloadCount ->((Object)this) instanceof Payloadc pay ? pay.payloads().size : 0;
        case size ->hitSize / tilesize;
        case color ->Color.toDoubleBits(team.color.r, team.color.g, team.color.b, 1.0F);
        default ->Float.NaN;
        };
    }

    @Override
    public float ammof() {
        return ammo / type.ammoCapacity;
    }

    @Override
    public float baseRotation() {
        return baseRotation;
    }

    @Override
    public float bounds() {
        return hitSize * 2.0F;
    }

    @Override
    public float clipSize() {
        if (isBuilding()) {
            return state.rules.infiniteResources ? Float.MAX_VALUE : Math.max(type.clipSize, type.region.width) + type.buildRange + tilesize * 4.0F;
        }
        if (mining()) {
            return type.clipSize + type.mineRange;
        }
        return type.clipSize;
    }

    @Override
    public float deltaAngle() {
        return Mathf.angle(deltaX, deltaY);
    }

    @Override
    public float deltaLen() {
        return Mathf.len(deltaX, deltaY);
    }

    @Override
    public float floorSpeedMultiplier() {
        Floor on = isFlying() || hovering ? Blocks.air.asFloor() : floorOn();
        return on.speedMultiplier * speedMultiplier;
    }

    @Override
    public float getDuration(StatusEffect effect) {
        var entry = statuses.find((e)->e.effect == effect);
        return entry == null ? 0 : entry.time;
    }

    @Override
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
        return y;
    }

    @Override
    public float healthf() {
        return health / maxHealth;
    }

    @Override
    public float hitSize() {
        return hitSize;
    }

    @Override
    public float mass() {
        return hitSize * hitSize * Mathf.pi;
    }

    @Override
    public float physicSize() {
        return hitSize * 0.7F;
    }

    @Override
    public float prefRotation() {
        if (activelyBuilding() && type.rotateToBuilding) {
            return angleTo(buildPlan());
        } else if (mineTile != null) {
            return angleTo(mineTile);
        } else if (moving() && type.omniMovement) {
            return vel().angle();
        }
        return rotation;
    }

    @Override
    public float range() {
        return type.maxRange;
    }

    @Override
    public float speed() {
        float strafePenalty = isGrounded() || !isPlayer() ? 1.0F : Mathf.lerp(1.0F, type.strafePenalty, Angles.angleDist(vel().angle(), rotation) / 180.0F);
        float boost = Mathf.lerp(1.0F, type.canBoost ? type.boostMultiplier : 1.0F, elevation);
        return type.speed * strafePenalty * boost * floorSpeedMultiplier();
    }

    @Override
    public float walkExtend(boolean scaled) {
        float raw = walkTime % (type.mechStride * 4);
        if (scaled) return raw / type.mechStride;
        if (raw > type.mechStride * 3) raw = raw - type.mechStride * 4; else if (raw > type.mechStride * 2) raw = type.mechStride * 2 - raw; else if (raw > type.mechStride) raw = type.mechStride * 2 - raw;
        return raw;
    }

    @Override
    public float walkExtension() {
        return walkExtension;
    }

    @Override
    public float walkTime() {
        return walkTime;
    }

    @Override
    public int cap() {
        return Units.getCap(team);
    }

    @Override
    public int classId() {
        return EntityRegistry.getID(MechUnit.class);
    }

    @Override
    public int count() {
        return team.data().countType(type);
    }

    @Override
    public int itemCapacity() {
        return type.itemCapacity;
    }

    @Override
    public int maxAccepted(Item item) {
        return stack.item != item && stack.amount > 0 ? 0 : itemCapacity() - stack.amount;
    }

    @Override
    public int pathType() {
        return Pathfinder.costGround;
    }

    @Override
    public int tileX() {
        return World.toTile(x);
    }

    @Override
    public int tileY() {
        return World.toTile(y);
    }

    @Override
    public Object senseObject(LAccess sensor) {
        return switch (sensor) {
        case type ->type;
        case name ->controller instanceof Player p ? p.name : null;
        case firstItem ->stack().amount == 0 ? null : item();
        case controller ->!isValid() ? null : controller instanceof LogicAI log ? log.controller : this;
        case payloadType ->((Object)this) instanceof Payloadc pay ? (pay.payloads().isEmpty() ? null : pay.payloads().peek() instanceof UnitPayload p1 ? p1.unit.type : pay.payloads().peek() instanceof BuildPayload p2 ? p2.block() : null) : null;
        default ->noSensed;
        };
    }

    @Override
    public String getControllerName() {
        if (isPlayer()) return getPlayer().name;
        if (controller instanceof LogicAI ai && ai.controller != null) return ai.controller.lastAccessed;
        return null;
    }

    @Override
    public String toString() {
        return "Unit#" + id() + ":" + type;
    }

    @Override
    public CommandAI command() {
        if (controller instanceof CommandAI ai) {
            return ai;
        } else {
            throw new IllegalArgumentException("Unit cannot be commanded - check isCommandable() first.");
        }
    }

    @Override
    public EntityCollisions.SolidPred solidity() {
        return isFlying() ? null : EntityCollisions::solid;
    }

    @Override
    public BuildPlan buildPlan() {
        return plans.size == 0 ? null : plans.first();
    }

    @Override
    public UnitController controller() {
        return controller;
    }

    @Override
    public Building buildOn() {
        return world.buildWorld(x, y);
    }

    @Override
    public Player getPlayer() {
        return isPlayer() ? (Player)controller : null;
    }

    @Override
    public Item getMineResult(Tile tile) {
        if (tile == null) return null;
        Item result;
        if (type.mineFloor && tile.block() == Blocks.air) {
            result = tile.drop();
        } else if (type.mineWalls) {
            result = tile.wallDrop();
        } else {
            return null;
        }
        return canMine(result) ? result : null;
    }

    @Override
    public Item item() {
        return stack.item;
    }

    @Override
    public Block blockOn() {
        Tile tile = tileOn();
        return tile == null ? Blocks.air : tile.block();
    }

    @Override
    public Tile tileOn() {
        return world.tileWorld(x, y);
    }

    @Override
    public Floor drownFloor() {
        if (hitSize >= 12 && canDrown()) {
            for (Point2 p : Geometry.d8) {
                Floor f = world.floorWorld(x + p.x * tilesize, y + p.y * tilesize);
                if (!f.isDeep()) {
                    return null;
                }
            }
        }
        return canDrown() ? floorOn() : null;
    }

    @Override
    public Floor floorOn() {
        Tile tile = tileOn();
        return tile == null || tile.block() != Blocks.air ? (Floor)Blocks.air : tile.floor();
    }

    @Override
    public CoreBlock.CoreBuild closestCore() {
        return state.teams.closestCore(x, y, team);
    }

    @Override
    public CoreBlock.CoreBuild closestEnemyCore() {
        return state.teams.closestEnemyCore(x, y, team);
    }

    @Override
    public CoreBlock.CoreBuild core() {
        return team.core();
    }

    @Override
    public void add() {
        if(added) return;
        Groups.unit.add(this);
        Groups.draw.add(this);
        Groups.sync.add(this);
        Groups.all.add(this);
        unit: {
            team.data().updateCount(type, 1);
            if (type.useUnitCap && count() > cap() && !spawnedByCore && !dead && !state.rules.editor) {
                Call.unitCapDeath(this);
                team.data().updateCount(type, -1);
            }
        }
        hitbox: {
            updateLastPosition();
        }
        entity: {
            added = true;
        }
    }

    @Override
    public void addBuild(BuildPlan place) {
        addBuild(place, true);
    }

    @Override
    public void addBuild(BuildPlan place, boolean tail) {
        if (!canBuild()) return;
        BuildPlan replace = null;
        for (BuildPlan plan : plans) {
            if (plan.x == place.x && plan.y == place.y) {
                replace = plan;
                break;
            }
        }
        if (replace != null) {
            plans.remove(replace);
        }
        Tile tile = world.tile(place.x, place.y);
        if (tile != null && tile.build instanceof ConstructBuild cons) {
            place.progress = cons.progress;
        }
        if (tail) {
            plans.addLast(place);
        } else {
            plans.addFirst(place);
        }
    }

    @Override
    public void addItem(Item item) {
        addItem(item, 1);
    }

    @Override
    public void addItem(Item item, int amount) {
        stack.amount = stack.item == item ? stack.amount + amount : amount;
        stack.item = item;
        stack.amount = Mathf.clamp(stack.amount, 0, itemCapacity());
    }

    @Override
    public void afterRead() {
        unit: {
            afterSync();
            if (!(controller instanceof AIController ai && ai.keepState())) {
                controller(type.createController(this));
            }
        }
        hitbox: {
            updateLastPosition();
        }
        builder: {
            if (plans == null) {
                plans = new Queue<>(1);
            }
        }
    }

    @Override
    public void afterSync() {
        unit: {
            setType(this.type);
            controller.unit(this);
        }
    }

    @Override
    public void aim(Position pos) {
        aim(pos.getX(), pos.getY());
    }

    @Override
    public void aim(float x, float y) {
        Tmp.v1.set(x, y).sub(this.x, this.y);
        if (Tmp.v1.len() < type.aimDst) Tmp.v1.setLength(type.aimDst);
        x = Tmp.v1.x + this.x;
        y = Tmp.v1.y + this.y;
        for (WeaponMount mount : mounts) {
            if (mount.weapon.controllable) {
                mount.aimX = x;
                mount.aimY = y;
            }
        }
        aimX = x;
        aimY = y;
    }

    @Override
    public void aimLook(Position pos) {
        aim(pos);
        lookAt(pos);
    }

    @Override
    public void aimLook(float x, float y) {
        aim(x, y);
        lookAt(x, y);
    }

    @Override
    public void apply(StatusEffect effect) {
        apply(effect, 1);
    }

    @Override
    public void apply(StatusEffect effect, float duration) {
        if (effect == StatusEffects.none || effect == null || isImmune(effect)) return;
        if (state.isCampaign()) {
            effect.unlock();
        }
        if (statuses.size > 0) {
            for (int i = 0; i < statuses.size; i++) {
                StatusEntry entry = statuses.get(i);
                if (entry.effect == effect) {
                    entry.time = Math.max(entry.time, duration);
                    effect.applied(this, entry.time, true);
                    return;
                } else if (entry.effect.applyTransition(this, effect, entry, duration)) {
                    return;
                }
            }
        }
        if (!effect.reactive) {
            StatusEntry entry = Pools.obtain(StatusEntry.class, StatusEntry::new);
            entry.set(effect, duration);
            statuses.add(entry);
            effect.applied(this, duration, false);
        }
    }

    @Override
    public void approach(Vec2 vector) {
        unit: {
            vel.approachDelta(vector, type.accel * speed());
        }
        mech: {
            if (!vector.isZero(0.001F)) {
                walked = true;
            }
        }
    }

    @Override
    public void baseRotation(float baseRotation) {
        this.baseRotation = baseRotation;
    }

    @Override
    public void clampHealth() {
        health = Math.min(health, maxHealth);
    }

    @Override
    public void clearBuilding() {
        plans.clear();
    }

    @Override
    public void clearItem() {
        stack.amount = 0;
    }

    @Override
    public void clearStatuses() {
        statuses.clear();
    }

    @Override
    public void collision(Hitboxc other, float x, float y) {
        unit: {
            if (other instanceof Bullet bullet) {
                controller.hit(bullet);
            }
        }
    }

    @Override
    public void controlWeapons(boolean rotate, boolean shoot) {
        for (WeaponMount mount : mounts) {
            if (mount.weapon.controllable) {
                mount.rotate = rotate;
                mount.shoot = shoot;
            }
        }
        isRotate = rotate;
        isShooting = shoot;
    }

    @Override
    public void controlWeapons(boolean rotateShoot) {
        controlWeapons(rotateShoot, rotateShoot);
    }

    @Override
    public void controller(UnitController next) {
        this.controller = next;
        if (controller.unit() != this) controller.unit(this);
    }

    @Override
    public void damage(float amount) {
        rawDamage(Damage.applyArmor(amount, armor) / healthMultiplier / Vars.state.rules.unitHealth(team));
    }

    @Override
    public void damage(float amount, boolean withEffect) {
        float pre = hitTime;
        damage(amount);
        if (!withEffect) {
            hitTime = pre;
        }
    }

    @Override
    public void damageContinuous(float amount) {
        damage(amount * Time.delta, hitTime <= -10 + hitDuration);
    }

    @Override
    public void damageContinuousPierce(float amount) {
        damagePierce(amount * Time.delta, hitTime <= -20 + hitDuration);
    }

    @Override
    public void damagePierce(float amount) {
        damagePierce(amount, true);
    }

    @Override
    public void damagePierce(float amount, boolean withEffect) {
        float pre = hitTime;
        rawDamage(amount / healthMultiplier / Vars.state.rules.unitHealth(team));
        if (!withEffect) {
            hitTime = pre;
        }
    }

    @Override
    public void destroy() {
        if (!isAdded() || !type.killable) return;
        float explosiveness = 2.0F + item().explosiveness * stack().amount * 1.53F;
        float flammability = item().flammability * stack().amount / 1.9F;
        float power = item().charge * Mathf.pow(stack().amount, 1.11F) * 160.0F;
        if (!spawnedByCore) {
            Damage.dynamicExplosion(x, y, flammability, explosiveness, power, (bounds() + type.legLength / 1.7F) / 2.0F, state.rules.damageExplosions && state.rules.unitCrashDamage(team) > 0, item().flammability > 1, team, type.deathExplosionEffect);
        } else {
            type.deathExplosionEffect.at(x, y, bounds() / 2.0F / 8.0F);
        }
        float shake = hitSize / 3.0F;
        if (type.createScorch) {
            Effect.scorch(x, y, (int)(hitSize / 5));
        }
        Effect.shake(shake, shake, this);
        type.deathSound.at(this);
        Events.fire(new UnitDestroyEvent(this));
        if (explosiveness > 7.0F && (isLocal() || wasPlayer)) {
            Events.fire(Trigger.suicideBomb);
        }
        for (WeaponMount mount : mounts) {
            if (mount.weapon.shootOnDeath && !(mount.weapon.bullet.killShooter && mount.totalShots > 0)) {
                mount.reload = 0.0F;
                mount.shoot = true;
                mount.weapon.update(this, mount);
            }
        }
        if (type.flying && !spawnedByCore && type.createWreck && state.rules.unitCrashDamage(team) > 0) {
            Damage.damage(team, x, y, Mathf.pow(hitSize, 0.94F) * 1.25F, Mathf.pow(hitSize, 0.75F) * type.crashDamageMultiplier * 5.0F * state.rules.unitCrashDamage(team), true, false, true);
        }
        if (!headless && type.createScorch) {
            for (int i = 0; i < type.wreckRegions.length; i++) {
                if (type.wreckRegions[i].found()) {
                    float range = type.hitSize / 4.0F;
                    Tmp.v1.rnd(range);
                    Effect.decal(type.wreckRegions[i], x + Tmp.v1.x, y + Tmp.v1.y, rotation - 90);
                }
            }
        }
        for (Ability a : abilities) {
            a.death(this);
        }
        type.killed(this);
        remove();
    }

    @Override
    public void display(Table table) {
        type.display(this, table);
    }

    @Override
    public void draw() {
        unit: {
            type.draw(this);
        }
        status: {
            for (StatusEntry e : statuses) {
                e.effect.draw(this, e.time);
            }
        }
        miner: {
            if (!mining()) break miner;
            float focusLen = hitSize / 2.0F + Mathf.absin(Time.time, 1.1F, 0.5F);
            float swingScl = 12.0F;
            float swingMag = tilesize / 8.0F;
            float flashScl = 0.3F;
            float px = x + Angles.trnsx(rotation, focusLen);
            float py = y + Angles.trnsy(rotation, focusLen);
            float ex = mineTile.worldx() + Mathf.sin(Time.time + 48, swingScl, swingMag);
            float ey = mineTile.worldy() + Mathf.sin(Time.time + 48, swingScl + 2.0F, swingMag);
            Draw.z(Layer.flyingUnit + 0.1F);
            Draw.color(Color.lightGray, Color.white, 1.0F - flashScl + Mathf.absin(Time.time, 0.5F, flashScl));
            Drawf.laser(Core.atlas.find("minelaser"), Core.atlas.find("minelaser-end"), px, py, ex, ey, 0.75F);
            if (isLocal()) {
                Lines.stroke(1.0F, Pal.accent);
                Lines.poly(mineTile.worldx(), mineTile.worldy(), 4, tilesize / 2.0F * Mathf.sqrt2, Time.time);
            }
            Draw.color();
        }
        builder: {
            drawBuilding();
        }
    }

    @Override
    public void drawBuildPlans() {
        Boolf<BuildPlan> skip = (plan)->plan.progress > 0.01F || (buildPlan() == plan && plan.initialized && (within(plan.x * tilesize, plan.y * tilesize, type.buildRange) || state.isEditor()));
        for (int i = 0; i < 2; i++) {
            for (BuildPlan plan : plans) {
                if (skip.get(plan)) continue;
                if (i == 0) {
                    drawPlan(plan, 1.0F);
                } else {
                    drawPlanTop(plan, 1.0F);
                }
            }
        }
        Draw.reset();
    }

    @Override
    public void drawBuilding() {
        boolean active = activelyBuilding();
        if (!active && lastActive == null) return;
        Draw.z(Layer.flyingUnit);
        BuildPlan plan = active ? buildPlan() : lastActive;
        Tile tile = plan.tile();
        var core = team.core();
        if (tile == null || !within(plan, state.rules.infiniteResources ? Float.MAX_VALUE : type.buildRange)) {
            return;
        }
        if (core != null && active && !isLocal() && !(tile.block() instanceof ConstructBlock)) {
            Draw.z(Layer.plans - 1.0F);
            drawPlan(plan, 0.5F);
            drawPlanTop(plan, 0.5F);
            Draw.z(Layer.flyingUnit);
        }
        if (type.drawBuildBeam) {
            float focusLen = type.buildBeamOffset + Mathf.absin(Time.time, 3.0F, 0.6F);
            float px = x + Angles.trnsx(rotation, focusLen);
            float py = y + Angles.trnsy(rotation, focusLen);
            drawBuildingBeam(px, py);
        }
    }

    @Override
    public void drawBuildingBeam(float px, float py) {
        boolean active = activelyBuilding();
        if (!active && lastActive == null) return;
        Draw.z(Layer.flyingUnit);
        BuildPlan plan = active ? buildPlan() : lastActive;
        Tile tile = world.tile(plan.x, plan.y);
        if (tile == null || !within(plan, state.rules.infiniteResources ? Float.MAX_VALUE : type.buildRange)) {
            return;
        }
        int size = plan.breaking ? active ? tile.block().size : lastSize : plan.block.size;
        float tx = plan.drawx();
        float ty = plan.drawy();
        Lines.stroke(1.0F, plan.breaking ? Pal.remove : Pal.accent);
        Draw.z(Layer.buildBeam);
        Draw.alpha(buildAlpha);
        if (!active && !(tile.build instanceof ConstructBuild)) {
            Fill.square(plan.drawx(), plan.drawy(), size * tilesize / 2.0F);
        }
        Drawf.buildBeam(px, py, tx, ty, Vars.tilesize * size / 2.0F);
        Fill.square(px, py, 1.8F + Mathf.absin(Time.time, 2.2F, 1.1F), rotation + 45);
        Draw.reset();
        Draw.z(Layer.flyingUnit);
    }

    @Override
    public void drawPlan(BuildPlan plan, float alpha) {
        plan.animScale = 1.0F;
        if (plan.breaking) {
            control.input.drawBreaking(plan);
        } else {
            plan.block.drawPlan(plan, control.input.allPlans(), Build.validPlace(plan.block, team, plan.x, plan.y, plan.rotation) || control.input.planMatches(plan), alpha);
        }
    }

    @Override
    public void drawPlanTop(BuildPlan plan, float alpha) {
        if (!plan.breaking) {
            Draw.reset();
            Draw.mixcol(Color.white, 0.24F + Mathf.absin(Time.globalTime, 6.0F, 0.28F));
            Draw.alpha(alpha);
            plan.block.drawPlanConfigTop(plan, plans);
        }
    }

    @Override
    public void getCollisions(Cons<QuadTree> consumer) {
    }

    @Override
    public void handleSyncHidden() {
        unit: {
            remove();
            netClient.clearRemovedEntity(id);
        }
    }

    @Override
    public void heal() {
        dead = false;
        health = maxHealth;
    }

    @Override
    public void heal(float amount) {
        unit: {
            if (health < maxHealth && amount > 0) {
                wasHealed = true;
            }
        }
        health: {
            health += amount;
            clampHealth();
        }
    }

    @Override
    public void healFract(float amount) {
        heal(amount * maxHealth);
    }

    @Override
    public void hitbox(Rect rect) {
        rect.setCentered(x, y, hitSize, hitSize);
    }

    @Override
    public void hitboxTile(Rect rect) {
        float size = Math.min(hitSize * 0.66F, 7.9F);
        rect.setCentered(x, y, size, size);
    }

    @Override
    public void impulse(Vec2 v) {
        impulse(v.x, v.y);
    }

    @Override
    public void impulse(float x, float y) {
        float mass = mass();
        vel.add(x / mass, y / mass);
    }

    @Override
    public void impulseNet(Vec2 v) {
        impulse(v.x, v.y);
        if (isRemote()) {
            float mass = mass();
            move(v.x / mass, v.y / mass);
        }
    }

    @Override
    public void interpolate() {
        if(lastUpdated != 0 && updateSpacing != 0) {
            float timeSinceUpdate = Time.timeSinceMillis(lastUpdated);
            float alpha = Math.min(timeSinceUpdate / updateSpacing, 2f);
            baseRotation = (Mathf.slerp(baseRotation_LAST_, baseRotation_TARGET_, alpha));
            rotation = (Mathf.slerp(rotation_LAST_, rotation_TARGET_, alpha));
            x = (Mathf.lerp(x_LAST_, x_TARGET_, alpha));
            y = (Mathf.lerp(y_LAST_, y_TARGET_, alpha));
        } else if(lastUpdated != 0) {
            baseRotation = baseRotation_TARGET_;
            rotation = rotation_TARGET_;
            x = x_TARGET_;
            y = y_TARGET_;
        }
    }

    @Override
    public void kill() {
        if (dead || net.client() || !type.killable) return;
        Call.unitDeath(id);
    }

    @Override
    public void killed() {
        unit: {
            wasPlayer = isLocal();
            health = Math.min(health, 0);
            dead = true;
            if (!type.flying || !type.createWreck) {
                destroy();
            }
        }
    }

    @Override
    public void landed() {
        unit: {
            if (type.mechLandShake > 0.0F) {
                Effect.shake(type.mechLandShake, type.mechLandShake, this);
            }
            type.landed(this);
        }
    }

    @Override
    public void lookAt(Position pos) {
        lookAt(angleTo(pos));
    }

    @Override
    public void lookAt(float angle) {
        rotation = Angles.moveToward(rotation, angle, type.rotateSpeed * Time.delta * speedMultiplier());
    }

    @Override
    public void lookAt(float x, float y) {
        lookAt(angleTo(x, y));
    }

    @Override
    public void move(Vec2 v) {
        move(v.x, v.y);
    }

    @Override
    public void move(float cx, float cy) {
        SolidPred check = solidity();
        if (check != null) {
            collisions.move(this, cx, cy, check);
        } else {
            x += cx;
            y += cy;
        }
    }

    @Override
    public void moveAt(Vec2 vector) {
        moveAt(vector, type.accel);
    }

    @Override
    public void moveAt(Vec2 vector, float acceleration) {
        mech: {
            if (!vector.isZero()) {
                walked = true;
            }
        }
        flying: {
            Vec2 t = tmp1.set(vector);
            tmp2.set(t).sub(vel).limit(acceleration * vector.len() * Time.delta);
            vel.add(tmp2);
        }
    }

    @Override
    public void movePref(Vec2 movement) {
        if (type.omniMovement) {
            moveAt(movement);
        } else {
            rotateMove(movement);
        }
    }

    @Override
    public void rawDamage(float amount) {
        boolean hadShields = shield > 1.0E-4F;
        if (hadShields) {
            shieldAlpha = 1.0F;
        }
        float shieldDamage = Math.min(Math.max(shield, 0), amount);
        shield -= shieldDamage;
        hitTime = 1.0F;
        amount -= shieldDamage;
        if (amount > 0 && type.killable) {
            health -= amount;
            if (health <= 0 && !dead) {
                kill();
            }
            if (hadShields && shield <= 1.0E-4F) {
                Fx.unitShieldBreak.at(x, y, 0, team.color, this);
            }
        }
    }

    @Override
    public void read(Reads read) {
        short REV = read.s();
        switch(REV) {
            case 0 -> {
                this.abilities = mindustry.io.TypeIO.readAbilities(read, this.abilities);
                this.ammo = read.f();
                this.baseRotation = read.f();
                this.controller = mindustry.io.TypeIO.readController(read, this.controller);
                this.elevation = read.f();
                this.flag = read.d();
                this.health = read.f();
                this.isShooting = read.bool();
                this.mineTile = mindustry.io.TypeIO.readTile(read);
                this.mounts = mindustry.io.TypeIO.readMounts(read, this.mounts);
                this.plans = mindustry.io.TypeIO.readPlansQueue(read);
                this.rotation = read.f();
                this.shield = read.f();
                this.spawnedByCore = read.bool();
                this.stack = mindustry.io.TypeIO.readItems(read, this.stack);
                int statuses_LENGTH = read.i();
                this.statuses.clear();
                for(int INDEX = 0; INDEX < statuses_LENGTH; INDEX ++) {
                    mindustry.entities.units.StatusEntry statuses_ITEM = mindustry.io.TypeIO.readStatus(read);
                    if(statuses_ITEM != null) this.statuses.add(statuses_ITEM);
                }
                this.team = mindustry.io.TypeIO.readTeam(read);
                this.type = Vars.content.getByID(ContentType.unit, read.s());
                this.updateBuilding = read.bool();
                this.vel = mindustry.io.TypeIO.readVec2(read, this.vel);
                this.x = read.f();
                this.y = read.f();
            }
            default -> {
                throw new IllegalArgumentException("Unknown revision '" + REV + "' for entities type 'MechUnit'");
            }
        }
        afterRead();
    }

    @Override
    public void readSync(Reads read) {
        if(lastUpdated != 0) updateSpacing = Time.timeSinceMillis(lastUpdated);
        lastUpdated = Time.millis();
        boolean islocal = isLocal();
        this.abilities = mindustry.io.TypeIO.readAbilities(read, this.abilities);
        this.ammo = read.f();
        if(!islocal) {
            baseRotation_LAST_ = this.baseRotation;
            this.baseRotation_TARGET_ = read.f();
        } else {
            read.f();
            baseRotation_LAST_ = this.baseRotation;
            baseRotation_TARGET_ = this.baseRotation;
        }
        this.controller = mindustry.io.TypeIO.readController(read, this.controller);
        if(!islocal) {
            this.elevation = read.f();
        } else {
            read.f();
        }
        this.flag = read.d();
        this.health = read.f();
        this.isShooting = read.bool();
        if(!islocal) {
            this.mineTile = mindustry.io.TypeIO.readTile(read);
        } else {
            mindustry.io.TypeIO.readTile(read);
        }
        if(!islocal) {
            this.mounts = mindustry.io.TypeIO.readMounts(read, this.mounts);
        } else {
            mindustry.io.TypeIO.readMounts(read);
        }
        if(!islocal) {
            this.plans = mindustry.io.TypeIO.readPlansQueue(read);
        } else {
            mindustry.io.TypeIO.readPlansQueue(read);
        }
        if(!islocal) {
            rotation_LAST_ = this.rotation;
            this.rotation_TARGET_ = read.f();
        } else {
            read.f();
            rotation_LAST_ = this.rotation;
            rotation_TARGET_ = this.rotation;
        }
        this.shield = read.f();
        this.spawnedByCore = read.bool();
        this.stack = mindustry.io.TypeIO.readItems(read, this.stack);
        int statuses_LENGTH = read.i();
        this.statuses.clear();
        for(int INDEX = 0; INDEX < statuses_LENGTH; INDEX ++) {
            mindustry.entities.units.StatusEntry statuses_ITEM = mindustry.io.TypeIO.readStatus(read);
            if(statuses_ITEM != null) this.statuses.add(statuses_ITEM);
        }
        this.team = mindustry.io.TypeIO.readTeam(read);
        this.type = Vars.content.getByID(ContentType.unit, read.s());
        if(!islocal) {
            this.updateBuilding = read.bool();
        } else {
            read.bool();
        }
        if(!islocal) {
            this.vel = mindustry.io.TypeIO.readVec2(read, this.vel);
        } else {
            mindustry.io.TypeIO.readVec2(read);
        }
        if(!islocal) {
            x_LAST_ = this.x;
            this.x_TARGET_ = read.f();
        } else {
            read.f();
            x_LAST_ = this.x;
            x_TARGET_ = this.x;
        }
        if(!islocal) {
            y_LAST_ = this.y;
            this.y_TARGET_ = read.f();
        } else {
            read.f();
            y_LAST_ = this.y;
            y_TARGET_ = this.y;
        }
        afterSync();
    }

    @Override
    public void readSyncManual(FloatBuffer buffer) {
        if(lastUpdated != 0) updateSpacing = Time.timeSinceMillis(lastUpdated);
        lastUpdated = Time.millis();
        this.baseRotation_LAST_ = this.baseRotation;
        this.baseRotation_TARGET_ = buffer.get();
        this.rotation_LAST_ = this.rotation;
        this.rotation_TARGET_ = buffer.get();
        this.x_LAST_ = this.x;
        this.x_TARGET_ = buffer.get();
        this.y_LAST_ = this.y;
        this.y_TARGET_ = buffer.get();
    }

    @Override
    public void remove() {
        if(!added) return;
        Groups.unit.remove(this);
        Groups.draw.remove(this);
        Groups.sync.remove(this);
        Groups.all.remove(this);
        unit: {
            team.data().updateCount(type, -1);
            controller.removed(this);
            if (trail != null && trail.size() > 0) {
                Fx.trailFade.at(x, y, trail.width(), type.trailColor == null ? team.color : type.trailColor, trail.copy());
            }
        }
        weapons: {
            for (WeaponMount mount : mounts) {
                if (mount.weapon.continuous && mount.bullet != null && mount.bullet.owner == this) {
                    mount.bullet.time = mount.bullet.lifetime - 10.0F;
                    mount.bullet = null;
                }
                if (mount.sound != null) {
                    mount.sound.stop();
                }
            }
        }
        sync: {
            if (Vars.net.client()) {
                Vars.netClient.addRemovedEntity(id());
            }
        }
        entity: {
            added = false;
        }
    }

    @Override
    public void removeBuild(int x, int y, boolean breaking) {
        int idx = plans.indexOf((req)->req.breaking == breaking && req.x == x && req.y == y);
        if (idx != -1) {
            plans.removeIndex(idx);
        }
    }

    @Override
    public void resetController() {
        controller(type.createController(this));
    }

    @Override
    public void rotateMove(Vec2 vec) {
        moveAt(Tmp.v2.trns(baseRotation, vec.len()));
        if (!vec.isZero()) {
            baseRotation = Angles.moveToward(baseRotation, vec.angle(), type.rotateSpeed * Math.max(Time.delta, 1));
        }
    }

    @Override
    public void set(Position pos) {
        set(pos.getX(), pos.getY());
    }

    @Override
    public void set(float x, float y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void set(UnitType def, UnitController controller) {
        if (this.type != def) {
            setType(def);
        }
        controller(controller);
    }

    @Override
    public void setProp(UnlockableContent content, double value) {
        if (content instanceof Item item) {
            stack.item = item;
            stack.amount = Mathf.clamp((int)value, 0, type.itemCapacity);
        }
    }

    @Override
    public void setProp(LAccess prop, double value) {
        switch (prop) {
        case health -> {
            health = (float)Mathf.clamp(value, 0, maxHealth);
            if (health <= 0.0F && !dead) {
                kill();
            }
        }
        case x -> x = World.unconv((float)value);
        case y -> y = World.unconv((float)value);
        case rotation -> rotation = (float)value;
        case team -> {
            if (!net.client()) {
                Team team = Team.get((int)value);
                if (controller instanceof Player p) {
                    p.team(team);
                }
                this.team = team;
            }
        }
        case flag -> flag = value;
        }
    }

    @Override
    public void setProp(LAccess prop, Object value) {
        switch (prop) {
        case team -> {
            if (value instanceof Team t && !net.client()) {
                if (controller instanceof Player p) p.team(t);
                team = t;
            }
        }
        case payloadType -> {
            if (((Object)this) instanceof Payloadc pay && !net.client()) {
                if (value instanceof Block b) {
                    Building build = b.newBuilding().create(b, team());
                    if (pay.canPickup(build)) pay.addPayload(new BuildPayload(build));
                } else if (value instanceof UnitType ut) {
                    Unit unit = ut.create(team());
                    if (pay.canPickup(unit)) pay.addPayload(new UnitPayload(unit));
                } else if (value == null && pay.payloads().size > 0) {
                    pay.payloads().pop();
                }
            }
        }
        }
    }

    @Override
    public void setType(UnitType type) {
        this.type = type;
        this.maxHealth = type.health;
        this.drag = type.drag;
        this.armor = type.armor;
        this.hitSize = type.hitSize;
        this.hovering = type.hovering;
        if (controller == null) controller(type.createController(this));
        if (mounts().length != type.weapons.size) setupWeapons(type);
        if (abilities.length != type.abilities.size) {
            abilities = new Ability[type.abilities.size];
            for (int i = 0; i < type.abilities.size; i++) {
                abilities[i] = type.abilities.get(i).copy();
            }
        }
    }

    @Override
    public void setWeaponRotation(float rotation) {
        for (WeaponMount mount : mounts) {
            mount.rotation = rotation;
        }
    }

    @Override
    public void setupWeapons(UnitType def) {
        mounts = new WeaponMount[def.weapons.size];
        for (int i = 0; i < mounts.length; i++) {
            mounts[i] = def.weapons.get(i).mountType.get(def.weapons.get(i));
        }
    }

    @Override
    public void snapInterpolation() {
        updateSpacing = 16;
        lastUpdated = Time.millis();
        baseRotation_LAST_ = baseRotation;
        baseRotation_TARGET_ = baseRotation;
        rotation_LAST_ = rotation;
        rotation_TARGET_ = rotation;
        x_LAST_ = x;
        x_TARGET_ = x;
        y_LAST_ = y;
        y_TARGET_ = y;
    }

    @Override
    public void snapSync() {
        updateSpacing = 16;
        lastUpdated = Time.millis();
        baseRotation_LAST_ = baseRotation_TARGET_;
        baseRotation = baseRotation_TARGET_;
        rotation_LAST_ = rotation_TARGET_;
        rotation = rotation_TARGET_;
        x_LAST_ = x_TARGET_;
        x = x_TARGET_;
        y_LAST_ = y_TARGET_;
        y = y_TARGET_;
    }

    @Override
    public void trns(Position pos) {
        trns(pos.getX(), pos.getY());
    }

    @Override
    public void trns(float x, float y) {
        set(this.x + x, this.y + y);
    }

    @Override
    public void unapply(StatusEffect effect) {
        statuses.remove((e)->{
            if (e.effect == effect) {
                Pools.free(e);
                return true;
            }
            return false;
        });
    }

    @Override
    public void unloaded() {
    }

    @Override
    public void update() {
        vel: {
            if (!net.client() || isLocal()) {
                float px = x;
                float py = y;
                move(vel.x * Time.delta, vel.y * Time.delta);
                if (Mathf.equal(px, x)) vel.x = 0;
                if (Mathf.equal(py, y)) vel.y = 0;
                vel.scl(Math.max(1.0F - drag * Time.delta, 0));
            }
        }
        unit: {
            type.update(this);
            if (wasHealed && healTime <= -1.0F) {
                healTime = 1.0F;
            }
            healTime -= Time.delta / 20.0F;
            wasHealed = false;
            if (team.isOnlyAI() && state.isCampaign() && state.getSector().isCaptured()) {
                kill();
            }
            if (!headless && type.loopSound != Sounds.none) {
                control.sound.loop(type.loopSound, this, type.loopSoundVolume);
            }
            if (!type.supportsEnv(state.rules.env) && !dead) {
                Call.unitEnvDeath(this);
                team.data().updateCount(type, -1);
            }
            if (state.rules.unitAmmo && ammo < type.ammoCapacity - 1.0E-4F) {
                resupplyTime += Time.delta;
                if (resupplyTime > 10.0F) {
                    type.ammoType.resupply(this);
                    resupplyTime = 0.0F;
                }
            }
            for (Ability a : abilities) {
                a.update(this);
            }
            if (trail != null) {
                trail.length = type.trailLength;
                float scale = type.useEngineElevation ? elevation : 1.0F;
                float offset = type.engineOffset / 2.0F + type.engineOffset / 2.0F * scale;
                float cx = x + Angles.trnsx(rotation + 180, offset);
                float cy = y + Angles.trnsy(rotation + 180, offset);
                trail.update(cx, cy);
            }
            drag = type.drag * (isGrounded() ? (floorOn().dragMultiplier) : 1.0F) * dragMultiplier * state.rules.dragMultiplier;
            if (team != state.rules.waveTeam && state.hasSpawns() && (!net.client() || isLocal()) && hittable()) {
                float relativeSize = state.rules.dropZoneRadius + hitSize / 2.0F + 1.0F;
                for (Tile spawn : spawner.getSpawns()) {
                    if (within(spawn.worldx(), spawn.worldy(), relativeSize)) {
                        velAddNet(Tmp.v1.set(this).sub(spawn.worldx(), spawn.worldy()).setLength(0.1F + 1.0F - dst(spawn) / relativeSize).scl(0.45F * Time.delta));
                    }
                }
            }
            if (dead || health <= 0) {
                drag = 0.01F;
                if (Mathf.chanceDelta(0.1)) {
                    Tmp.v1.rnd(Mathf.range(hitSize));
                    type.fallEffect.at(x + Tmp.v1.x, y + Tmp.v1.y);
                }
                if (Mathf.chanceDelta(0.2)) {
                    float offset = type.engineOffset / 2.0F + type.engineOffset / 2.0F * elevation;
                    float range = Mathf.range(type.engineSize);
                    type.fallEngineEffect.at(x + Angles.trnsx(rotation + 180, offset) + Mathf.range(range), y + Angles.trnsy(rotation + 180, offset) + Mathf.range(range), Mathf.random());
                }
                elevation -= type.fallSpeed * Time.delta;
                if (isGrounded() || health <= -maxHealth) {
                    Call.unitDestroy(id);
                }
            }
            Tile tile = tileOn();
            Floor floor = floorOn();
            if (tile != null && isGrounded() && !type.hovering) {
                if (tile.build != null) {
                    tile.build.unitOn(this);
                }
                if (floor.damageTaken > 0.0F) {
                    damageContinuous(floor.damageTaken);
                }
            }
            if (tile != null && !canPassOn()) {
                if (type.canBoost) {
                    elevation = 1.0F;
                } else if (!net.client()) {
                    kill();
                }
            }
            if (!net.client() && !dead) {
                controller.updateUnit();
            }
            if (!controller.isValidController()) {
                resetController();
            }
            if (spawnedByCore && !isPlayer() && !dead) {
                Call.unitDespawn(this);
            }
        }
        mech: {
            if (walked || net.client()) {
                float len = deltaLen();
                baseRotation = Angles.moveToward(baseRotation, deltaAngle(), type().baseRotateSpeed * Mathf.clamp(len / type().speed / Time.delta) * Time.delta);
                walkTime += len;
                walked = false;
            }
            float extend = walkExtend(false);
            float base = walkExtend(true);
            float extendScl = base % 1.0F;
            float lastExtend = walkExtension;
            if (!headless && extendScl < lastExtend && base % 2.0F > 1.0F && !isFlying() && !inFogTo(player.team())) {
                int side = -Mathf.sign(extend);
                float width = hitSize / 2.0F * side;
                float length = type.mechStride * 1.35F;
                float cx = x + Angles.trnsx(baseRotation, length, width);
                float cy = y + Angles.trnsy(baseRotation, length, width);
                if (type.stepShake > 0) {
                    Effect.shake(type.stepShake, type.stepShake, cx, cy);
                }
                if (type.mechStepParticles) {
                    Effect.floorDust(cx, cy, hitSize / 8.0F);
                }
            }
            walkExtension = extendScl;
        }
        status: {
            Floor floor = floorOn();
            if (isGrounded() && !type.hovering) {
                apply(floor.status, floor.statusDuration);
            }
            applied.clear();
            speedMultiplier = damageMultiplier = healthMultiplier = reloadMultiplier = buildSpeedMultiplier = dragMultiplier = 1.0F;
            disarmed = false;
            if (statuses.isEmpty()) break status;
            int index = 0;
            while (index < statuses.size) {
                StatusEntry entry = statuses.get(index++);
                entry.time = Math.max(entry.time - Time.delta, 0);
                if (entry.effect == null || (entry.time <= 0 && !entry.effect.permanent)) {
                    Pools.free(entry);
                    index--;
                    statuses.remove(index);
                } else {
                    applied.set(entry.effect.id);
                    speedMultiplier *= entry.effect.speedMultiplier;
                    healthMultiplier *= entry.effect.healthMultiplier;
                    damageMultiplier *= entry.effect.damageMultiplier;
                    reloadMultiplier *= entry.effect.reloadMultiplier;
                    buildSpeedMultiplier *= entry.effect.buildSpeedMultiplier;
                    dragMultiplier *= entry.effect.dragMultiplier;
                    disarmed |= entry.effect.disarm;
                    entry.effect.update(this, entry.time);
                }
            }
        }
        miner: {
            if (mineTile == null) break miner;
            Building core = closestCore();
            Item item = getMineResult(mineTile);
            if (core != null && item != null && !acceptsItem(item) && within(core, mineTransferRange) && !offloadImmediately()) {
                int accepted = core.acceptStack(item(), stack().amount, this);
                if (accepted > 0) {
                    Call.transferItemTo(this, item(), accepted, mineTile.worldx() + Mathf.range(tilesize / 2.0F), mineTile.worldy() + Mathf.range(tilesize / 2.0F), core);
                    clearItem();
                }
            }
            if ((!net.client() || isLocal()) && !validMine(mineTile)) {
                mineTile = null;
                mineTimer = 0.0F;
            } else if (mining() && item != null) {
                mineTimer += Time.delta * type.mineSpeed;
                if (Mathf.chance(0.06 * Time.delta)) {
                    Fx.pulverizeSmall.at(mineTile.worldx() + Mathf.range(tilesize / 2.0F), mineTile.worldy() + Mathf.range(tilesize / 2.0F), 0.0F, item.color);
                }
                if (mineTimer >= 50.0F + (type.mineHardnessScaling ? item.hardness * 15.0F : 15.0F)) {
                    mineTimer = 0;
                    if (state.rules.sector != null && team() == state.rules.defaultTeam) state.rules.sector.info.handleProduction(item, 1);
                    if (core != null && within(core, mineTransferRange) && core.acceptStack(item, 1, this) == 1 && offloadImmediately()) {
                        if (item() == item && !net.client()) addItem(item);
                        Call.transferItemTo(this, item, 1, mineTile.worldx() + Mathf.range(tilesize / 2.0F), mineTile.worldy() + Mathf.range(tilesize / 2.0F), core);
                    } else if (acceptsItem(item)) {
                        InputHandler.transferItemToUnit(item, mineTile.worldx() + Mathf.range(tilesize / 2.0F), mineTile.worldy() + Mathf.range(tilesize / 2.0F), this);
                    } else {
                        mineTile = null;
                        mineTimer = 0.0F;
                    }
                }
                if (!headless) {
                    control.sound.loop(type.mineSound, this, type.mineSoundVolume);
                }
            }
        }
        items: {
            stack.amount = Mathf.clamp(stack.amount, 0, itemCapacity());
            itemTime = Mathf.lerpDelta(itemTime, Mathf.num(hasItem()), 0.05F);
        }
        flying: {
            Floor floor = floorOn();
            if (isFlying() != wasFlying) {
                if (wasFlying) {
                    if (tileOn() != null) {
                        Fx.unitLand.at(x, y, floorOn().isLiquid ? 1.0F : 0.5F, tileOn().floor().mapColor);
                    }
                }
                wasFlying = isFlying();
            }
            if (!hovering && isGrounded()) {
                if ((splashTimer += Mathf.dst(deltaX(), deltaY())) >= (7.0F + hitSize() / 8.0F)) {
                    floor.walkEffect.at(x, y, hitSize() / 8.0F, floor.mapColor);
                    splashTimer = 0.0F;
                    if (emitWalkSound()) {
                        floor.walkSound.at(x, y, Mathf.random(floor.walkSoundPitchMin, floor.walkSoundPitchMax), floor.walkSoundVolume);
                    }
                }
            }
            updateDrowning();
        }
        weapons: {
            for (WeaponMount mount : mounts) {
                mount.weapon.update(this, mount);
            }
        }
        builder: {
            updateBuildLogic();
        }
        health: {
            hitTime -= Time.delta / hitDuration;
        }
        bounded: {
            if (!type.bounded) break bounded;
            float bot = 0.0F;
            float left = 0.0F;
            float top = world.unitHeight();
            float right = world.unitWidth();
            if (state.rules.limitMapArea && !team.isAI()) {
                bot = state.rules.limitY * tilesize;
                left = state.rules.limitX * tilesize;
                top = state.rules.limitHeight * tilesize + bot;
                right = state.rules.limitWidth * tilesize + left;
            }
            if (!net.client() || isLocal()) {
                float dx = 0.0F;
                float dy = 0.0F;
                if (x < left) dx += (-(x - left) / warpDst);
                if (y < bot) dy += (-(y - bot) / warpDst);
                if (x > right) dx -= (x - right) / warpDst;
                if (y > top) dy -= (y - top) / warpDst;
                velAddNet(dx * Time.delta, dy * Time.delta);
            }
            if (isGrounded()) {
                x = Mathf.clamp(x, left, right - tilesize);
                y = Mathf.clamp(y, bot, top - tilesize);
            }
            if (x < -finalWorldBounds + left || y < -finalWorldBounds + bot || x >= right + finalWorldBounds || y >= top + finalWorldBounds) {
                kill();
            }
        }
        sync: {
            if ((Vars.net.client() && !isLocal()) || isRemote()) {
                interpolate();
            }
        }
        shield: {
            shieldAlpha -= Time.delta / 15.0F;
            if (shieldAlpha < 0) shieldAlpha = 0.0F;
        }
    }

    @Override
    public void updateBoosting(boolean boost) {
        if (!type.canBoost || dead) return;
        elevation = Mathf.approachDelta(elevation, type.canBoost ? Mathf.num(boost || onSolid() || (isFlying() && !canLand())) : 0.0F, type.riseSpeed);
    }

    @Override
    public void updateBuildLogic() {
        if (type.buildSpeed <= 0.0F) return;
        if (!headless) {
            if (lastActive != null && buildAlpha <= 0.01F) {
                lastActive = null;
            }
            buildAlpha = Mathf.lerpDelta(buildAlpha, activelyBuilding() ? 1.0F : 0.0F, 0.15F);
        }
        if (!updateBuilding || !canBuild()) {
            validatePlans();
            return;
        }
        float finalPlaceDst = state.rules.infiniteResources ? Float.MAX_VALUE : type.buildRange;
        boolean infinite = state.rules.infiniteResources || team().rules().infiniteResources;
        buildCounter += Time.delta;
        if (Float.isNaN(buildCounter) || Float.isInfinite(buildCounter)) buildCounter = 0.0F;
        buildCounter = Math.min(buildCounter, 10.0F);
        int maxPerFrame = 10;
        int count = 0;
        while (buildCounter >= 1 && count++ < maxPerFrame) {
            buildCounter -= 1.0F;
            validatePlans();
            var core = core();
            if (buildPlan() == null) return;
            if (plans.size > 1) {
                int total = 0;
                int size = plans.size;
                BuildPlan plan;
                while ((!within((plan = buildPlan()).tile(), finalPlaceDst) || shouldSkip(plan, core)) && total < size) {
                    plans.removeFirst();
                    plans.addLast(plan);
                    total++;
                }
            }
            BuildPlan current = buildPlan();
            Tile tile = current.tile();
            lastActive = current;
            buildAlpha = 1.0F;
            if (current.breaking) lastSize = tile.block().size;
            if (!within(tile, finalPlaceDst)) continue;
            if (!headless) {
                Vars.control.sound.loop(Sounds.build, tile, 0.15F);
            }
            if (!(tile.build instanceof ConstructBuild cb)) {
                if (!current.initialized && !current.breaking && Build.validPlace(current.block, team, current.x, current.y, current.rotation)) {
                    boolean hasAll = infinite || current.isRotation(team) || !Structs.contains(current.block.requirements, (i)->core != null && !core.items.has(i.item, Math.min(Mathf.round(i.amount * state.rules.buildCostMultiplier), 1)));
                    if (hasAll) {
                        Call.beginPlace(this, current.block, team, current.x, current.y, current.rotation);
                    } else {
                        current.stuck = true;
                    }
                } else if (!current.initialized && current.breaking && Build.validBreak(team, current.x, current.y)) {
                    Call.beginBreak(this, team, current.x, current.y);
                } else {
                    plans.removeFirst();
                    continue;
                }
            } else if ((tile.team() != team && tile.team() != Team.derelict) || (!current.breaking && (cb.current != current.block || cb.tile != current.tile()))) {
                plans.removeFirst();
                continue;
            }
            if (tile.build instanceof ConstructBuild && !current.initialized) {
                Events.fire(new BuildSelectEvent(tile, team, this, current.breaking));
                current.initialized = true;
            }
            if ((core == null && !infinite) || !(tile.build instanceof ConstructBuild entity)) {
                continue;
            }
            float bs = 1.0F / entity.buildCost * type.buildSpeed * buildSpeedMultiplier * state.rules.buildSpeed(team);
            if (current.breaking) {
                entity.deconstruct(this, core, bs);
            } else {
                entity.construct(this, core, bs, current.config);
            }
            current.stuck = Mathf.equal(current.progress, entity.progress);
            current.progress = entity.progress;
        }
    }

    @Override
    public void updateDrowning() {
        Floor floor = drownFloor();
        if (floor != null && floor.isLiquid && floor.drownTime > 0) {
            lastDrownFloor = floor;
            drownTime += Time.delta / floor.drownTime / type.drownTimeMultiplier;
            if (Mathf.chanceDelta(0.05F)) {
                floor.drownUpdateEffect.at(x, y, hitSize, floor.mapColor);
            }
            if (drownTime >= 0.999F && !net.client()) {
                kill();
                Events.fire(new UnitDrownEvent(this));
            }
        } else {
            drownTime -= Time.delta / 50.0F;
        }
        drownTime = Mathf.clamp(drownTime);
    }

    @Override
    public void updateLastPosition() {
        deltaX = x - lastX;
        deltaY = y - lastY;
        lastX = x;
        lastY = y;
    }

    @Override
    public void validatePlans() {
        if (plans.size > 0) {
            Iterator<BuildPlan> it = plans.iterator();
            while (it.hasNext()) {
                BuildPlan plan = it.next();
                Tile tile = world.tile(plan.x, plan.y);
                if (tile == null || (plan.breaking && tile.block() == Blocks.air) || (!plan.breaking && ((tile.build != null && tile.build.rotation == plan.rotation) || !plan.block.rotate) && (tile.block() == plan.block || (plan.block != null && (plan.block.isOverlay() && plan.block == tile.overlay() || (plan.block.isFloor() && plan.block == tile.floor())))))) {
                    it.remove();
                }
            }
        }
    }

    @Override
    public void velAddNet(Vec2 v) {
        vel.add(v);
        if (isRemote()) {
            x += v.x;
            y += v.y;
        }
    }

    @Override
    public void velAddNet(float vx, float vy) {
        vel.add(vx, vy);
        if (isRemote()) {
            x += vx;
            y += vy;
        }
    }

    @Override
    public void walkExtension(float walkExtension) {
        this.walkExtension = walkExtension;
    }

    @Override
    public void walkTime(float walkTime) {
        this.walkTime = walkTime;
    }

    @Override
    public void wobble() {
        x += Mathf.sin(Time.time + (id() % 10) * 12, 25.0F, 0.05F) * Time.delta * elevation;
        y += Mathf.cos(Time.time + (id() % 10) * 12, 25.0F, 0.05F) * Time.delta * elevation;
    }

    @Override
    public void write(Writes write) {
        write.s(0);
        mindustry.io.TypeIO.writeAbilities(write, this.abilities);
        write.f(this.ammo);
        write.f(this.baseRotation);
        mindustry.io.TypeIO.writeController(write, this.controller);
        write.f(this.elevation);
        write.d(this.flag);
        write.f(this.health);
        write.bool(this.isShooting);
        mindustry.io.TypeIO.writeTile(write, this.mineTile);
        mindustry.io.TypeIO.writeMounts(write, this.mounts);
        write.i(this.plans.size);
        for(int INDEX = 0; INDEX < this.plans.size; INDEX ++) {
            mindustry.io.TypeIO.writePlan(write, this.plans.get(INDEX));
        }
        write.f(this.rotation);
        write.f(this.shield);
        write.bool(this.spawnedByCore);
        mindustry.io.TypeIO.writeItems(write, this.stack);
        write.i(this.statuses.size);
        for(int INDEX = 0; INDEX < this.statuses.size; INDEX ++) {
            mindustry.io.TypeIO.writeStatus(write, this.statuses.get(INDEX));
        }
        mindustry.io.TypeIO.writeTeam(write, this.team);
        write.s(this.type.id);
        write.bool(this.updateBuilding);
        mindustry.io.TypeIO.writeVec2(write, this.vel);
        write.f(this.x);
        write.f(this.y);
    }

    @Override
    public void writeSync(Writes write) {
        mindustry.io.TypeIO.writeAbilities(write, this.abilities);
        write.f(this.ammo);
        write.f(this.baseRotation);
        mindustry.io.TypeIO.writeController(write, this.controller);
        write.f(this.elevation);
        write.d(this.flag);
        write.f(this.health);
        write.bool(this.isShooting);
        mindustry.io.TypeIO.writeTile(write, this.mineTile);
        mindustry.io.TypeIO.writeMounts(write, this.mounts);
        mindustry.io.TypeIO.writePlansQueueNet(write, this.plans);
        write.f(this.rotation);
        write.f(this.shield);
        write.bool(this.spawnedByCore);
        mindustry.io.TypeIO.writeItems(write, this.stack);
        write.i(this.statuses.size);
        for(int INDEX = 0; INDEX < this.statuses.size; INDEX ++) {
            mindustry.io.TypeIO.writeStatus(write, this.statuses.get(INDEX));
        }
        mindustry.io.TypeIO.writeTeam(write, this.team);
        write.s(this.type.id);
        write.bool(this.updateBuilding);
        mindustry.io.TypeIO.writeVec2(write, this.vel);
        write.f(this.x);
        write.f(this.y);
    }

    @Override
    public void writeSyncManual(FloatBuffer buffer) {
        buffer.put(this.baseRotation);
        buffer.put(this.rotation);
        buffer.put(this.x);
        buffer.put(this.y);
    }

    public static MechUnit create() {
        return new MechUnit();
    }
}
