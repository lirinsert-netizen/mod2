package com.zombiehorde.client.model;

import com.zombiehorde.entity.EmberZombie;
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

public final class EmberZombieModel extends ZombieModel<EmberZombie> {
    public EmberZombieModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = HumanoidModel.createMesh(new CubeDeformation(0.12F), 0.0F);
        PartDefinition root = mesh.getRoot();

        root.getChild("head").addOrReplaceChild("left_horn",
                CubeListBuilder.create()
                        .texOffs(32, 0)
                        .addBox(1.5F, -10.0F, -1.0F, 2.0F, 4.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.2F, 0.0F, 0.35F));

        root.getChild("head").addOrReplaceChild("right_horn",
                CubeListBuilder.create()
                        .texOffs(32, 0)
                        .mirror()
                        .addBox(-3.5F, -10.0F, -1.0F, 2.0F, 4.0F, 2.0F)
                        .mirror(false),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.2F, 0.0F, -0.35F));

        root.getChild("body").addOrReplaceChild("core",
                CubeListBuilder.create()
                        .texOffs(40, 0)
                        .addBox(-2.0F, 4.0F, -3.2F, 4.0F, 4.0F, 1.0F),
                PartPose.ZERO);

        root.getChild("right_arm").addOrReplaceChild("ember_spike",
                CubeListBuilder.create()
                        .texOffs(32, 6)
                        .addBox(-4.8F, 2.0F, -3.2F, 2.0F, 4.0F, 1.0F),
                PartPose.ZERO);

        root.getChild("left_arm").addOrReplaceChild("ember_spike",
                CubeListBuilder.create()
                        .texOffs(32, 6)
                        .mirror()
                        .addBox(2.8F, 2.0F, -3.2F, 2.0F, 4.0F, 1.0F)
                        .mirror(false),
                PartPose.ZERO);

        return LayerDefinition.create(mesh, 64, 64);
    }

    @Override
    public void setupAnim(EmberZombie entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

        this.body.y = 0.0F;
        this.head.y = 0.0F;

        float heatWave = Mth.sin(ageInTicks * 0.25F) * 0.06F;
        this.body.y += heatWave * 1.6F;
        this.rightArm.zRot += 0.12F + heatWave;
        this.leftArm.zRot -= 0.12F + heatWave;
        this.head.y += heatWave;
        this.hat.copyFrom(this.head);
    }
}
