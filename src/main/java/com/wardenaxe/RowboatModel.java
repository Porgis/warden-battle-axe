package com.wardenaxe;

import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

/**
 * The Rowboat's 3D model: a vanilla-style chest boat, but 16 units (1 block) longer
 * so there is room for a second seat behind the driver, with the chest at the very back.
 * The hull runs from x = -22 to x = +22 (44 units = 2.75 blocks); the front of the boat is +x.
 */
public class RowboatModel {
    public static LayerDefinition createLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        // Hull
        root.addOrReplaceChild("bottom",
                CubeListBuilder.create().texOffs(0, 0).addBox(-22.0F, -9.0F, -3.0F, 44.0F, 16.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 3.0F, 1.0F, (float) (Math.PI / 2), 0.0F, 0.0F));
        root.addOrReplaceChild("back",
                CubeListBuilder.create().texOffs(0, 19).addBox(-13.0F, -7.0F, -1.0F, 18.0F, 6.0F, 2.0F),
                PartPose.offsetAndRotation(-23.0F, 4.0F, 4.0F, 0.0F, (float) (Math.PI * 3.0 / 2.0), 0.0F));
        root.addOrReplaceChild("front",
                CubeListBuilder.create().texOffs(0, 27).addBox(-8.0F, -7.0F, -1.0F, 16.0F, 6.0F, 2.0F),
                PartPose.offsetAndRotation(23.0F, 4.0F, 0.0F, 0.0F, (float) (Math.PI / 2), 0.0F));
        root.addOrReplaceChild("right",
                CubeListBuilder.create().texOffs(0, 35).addBox(-22.0F, -7.0F, -1.0F, 44.0F, 6.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 4.0F, -9.0F, 0.0F, (float) Math.PI, 0.0F));
        root.addOrReplaceChild("left",
                CubeListBuilder.create().texOffs(0, 43).addBox(-22.0F, -7.0F, -1.0F, 44.0F, 6.0F, 2.0F),
                PartPose.offset(0.0F, 4.0F, 9.0F));

        // Paddles
        root.addOrReplaceChild("left_paddle",
                CubeListBuilder.create().texOffs(62, 0)
                        .addBox(-1.0F, 0.0F, -5.0F, 2.0F, 2.0F, 18.0F)
                        .addBox(-1.001F, -3.0F, 8.0F, 1.0F, 6.0F, 7.0F),
                PartPose.offsetAndRotation(3.0F, -5.0F, 9.0F, 0.0F, 0.0F, (float) (Math.PI / 16)));
        root.addOrReplaceChild("right_paddle",
                CubeListBuilder.create().texOffs(62, 20)
                        .addBox(-1.0F, 0.0F, -5.0F, 2.0F, 2.0F, 18.0F)
                        .addBox(-1.001F, -3.0F, 8.0F, 1.0F, 6.0F, 7.0F),
                PartPose.offsetAndRotation(3.0F, -5.0F, -9.0F, 0.0F, (float) Math.PI, (float) (Math.PI / 16)));

        // Keeps water out of the boat
        root.addOrReplaceChild("water_patch",
                CubeListBuilder.create().texOffs(0, 0).addBox(-22.0F, -9.0F, -3.0F, 44.0F, 16.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, -3.0F, 1.0F, (float) (Math.PI / 2), 0.0F, 0.0F));

        // Chest, at the very back of the boat
        root.addOrReplaceChild("chest_bottom",
                CubeListBuilder.create().texOffs(0, 76).addBox(0.0F, 0.0F, 0.0F, 12.0F, 8.0F, 12.0F),
                PartPose.offsetAndRotation(-10.0F, -5.0F, -6.0F, 0.0F, (float) (-Math.PI / 2), 0.0F));
        root.addOrReplaceChild("chest_lid",
                CubeListBuilder.create().texOffs(0, 59).addBox(0.0F, 0.0F, 0.0F, 12.0F, 4.0F, 12.0F),
                PartPose.offsetAndRotation(-10.0F, -9.0F, -6.0F, 0.0F, (float) (-Math.PI / 2), 0.0F));
        root.addOrReplaceChild("chest_lock",
                CubeListBuilder.create().texOffs(0, 59).addBox(0.0F, 0.0F, 0.0F, 2.0F, 4.0F, 1.0F),
                PartPose.offsetAndRotation(-9.0F, -6.0F, -1.0F, 0.0F, (float) (-Math.PI / 2), 0.0F));

        return LayerDefinition.create(mesh, 128, 128);
    }
}
