/*
 ** 2013 October 24
 **
 ** The author disclaims copyright to this source code.  In place of
 ** a legal notice, here is a blessing:
 **    May you do good and not evil.
 **    May you find forgiveness for yourself and forgive others.
 **    May you share freely, never taking more than you give.
 */
package info.ata4.minecraft.dragon.server.entity.breeds;

import info.ata4.minecraft.dragon.server.entity.EntityTameableDragon;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.entity.ai.EntityAIRestrictSun;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Nico Bergemann <barracuda415 at yahoo.de>
 */
public class DragonBreedGold extends DragonBreed {
    
    private final Map<EntityTameableDragon, EntityAIRestrictSun> appliedTasks = new HashMap<>();

    DragonBreedGold() {
        super("gold", 0xd5b861);
        
        addHabitatBlock(Blocks.GOLD_BLOCK);
        addHabitatBlock(Blocks.GOLD_ORE);
    }
    
    @Override
    public void onEnable(EntityTameableDragon dragon) {
        EntityAIRestrictSun restrictSun = new EntityAIRestrictSun(dragon);
        dragon.tasks.addTask(2, restrictSun);
        appliedTasks.put(dragon, restrictSun);
    }
    
    @Override
    public void onDisable(EntityTameableDragon dragon) {
        if (appliedTasks.containsKey(dragon)) {
            dragon.tasks.removeTask(appliedTasks.get(dragon));
            appliedTasks.remove(dragon);
        }
    }
    
    @Override
    public boolean isHabitatEnvironment(EntityTameableDragon dragon) {
        if (dragon.posY > dragon.worldObj.getHeight() * 0.25) {
            // woah dude, too high!
            return false;
        }

        BlockPos pos = dragon.getPosition();
        
        if (dragon.worldObj.canBlockSeeSky(pos)) {
             // sun is shining!
            return false;
        }
        
        if (dragon.worldObj.getLight(pos) > 4) {
            // too bright!
            return false;
        }

        return true;
    }

    @Override
    public void onDeath(EntityTameableDragon dragon) {
    }

}
