package com.zombiehorde.client.model;

import com.zombiehorde.entity.BruteZombie;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.ZombieModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

public final class BruteZombieModel extends ZombieModel<BruteZombie> {
    public BruteZombieModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = HumanoidModel.createMesh(new CubeDeformation(0.45F), 0.0F);
        PartDefinition root = mesh.getRoot();

        root.getChild("head").addOrReplaceChild("jaw_plate",
                CubeListBuilder.create()
                        .texOffs(32, 0)
                        .addBox(-4.0F, -1.0F, -5.5F, 8.0F, 3.0F, 2.0F),
                PartPose.ZERO);

        root.getChild("body").addOrReplaceChild("chest_plate",
                CubeListBuilder.create()
                        .texOffs(0, 32)
                        .addBox(-5.5F, 0.0F, -3.6F, 11.0F, 13.0F, 2.0F),
                PartPose.ZERO);

        root.getChild("right_arm").addOrReplaceChild("shoulder",
                CubeListBuilder.create()
                        .texOffs(32, 5)
                        .addBox(-5.7F, -3.0F, -3.2F, 5.0F, 4.0F, 5.0F),
                PartPose.ZERO);

        root.getChild("left_arm").addOrReplaceChild("shoulder",
                CubeListBuilder.create()
                        .texOffs(32, 5)
                        .mirror()
                        .addBox(0.7F, -3.0F, -3.2F, 5.0F, 4.0F, 5.0F)
                        .mirror(false),
                PartPose.ZERO);

        return LayerDefinition.create(mesh, 64, 64);
    }

    @Override
    public void setupAnim(BruteZombie entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        super.setupAnim(entity, limbSwing, limbSwingAmount * 0.55F, ageInTicks, netHeadYaw, headPitch);

        this.body.zRot = 0.0F;

        float idleSway = Mth.sin(ageInTicks * 0.08F) * 0.04F;
        this.body.zRot = idleSway;
        this.rightArm.zRot += 0.1F + idleSway;
        this.leftArm.zRot -= 0.1F + idleSway;
        this.rightLeg.xRot *= 0.7F;
        this.leftLeg.xRot *= 0.7F;
        this.hat.copyFrom(this.head);
    }
}
