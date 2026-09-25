package com.zombiehorde.client.model;

import com.zombiehorde.entity.RunnerZombie;
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

public final class RunnerZombieModel extends ZombieModel<RunnerZombie> {
    public RunnerZombieModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = HumanoidModel.createMesh(new CubeDeformation(-0.2F), 0.0F);
        PartDefinition root = mesh.getRoot();

        root.getChild("head").addOrReplaceChild("crest",
                CubeListBuilder.create()
                        .texOffs(32, 0)
                        .addBox(-1.0F, -10.0F, -1.0F, 2.0F, 4.0F, 2.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        root.getChild("body").addOrReplaceChild("bandolier",
                CubeListBuilder.create()
                        .texOffs(40, 0)
                        .addBox(-4.0F, 0.0F, -2.7F, 8.0F, 3.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 2.0F, 0.0F, -0.2F, 0.0F, 0.0F));

        root.getChild("right_leg").addOrReplaceChild("knee_guard",
                CubeListBuilder.create()
                        .texOffs(32, 6)
                        .addBox(-2.2F, 4.0F, -2.2F, 4.0F, 2.0F, 1.0F),
                PartPose.ZERO);

        root.getChild("left_leg").addOrReplaceChild("knee_guard",
                CubeListBuilder.create()
                        .texOffs(32, 6)
                        .mirror()
                        .addBox(-1.8F, 4.0F, -2.2F, 4.0F, 2.0F, 1.0F)
                        .mirror(false),
                PartPose.ZERO);

        return LayerDefinition.create(mesh, 64, 64);
    }

    @Override
    public void setupAnim(RunnerZombie entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

        this.body.xRot = 0.0F;
        this.head.xRot = headPitch * ((float)Math.PI / 180F);
        this.head.yRot = netHeadYaw * ((float)Math.PI / 180F);

        float runLean = 0.45F + limbSwingAmount * 0.25F;
        this.body.xRot += runLean;
        this.head.xRot -= runLean * 0.55F;
        this.rightArm.xRot -= 0.25F;
        this.leftArm.xRot -= 0.25F;
        this.rightArm.zRot = Mth.cos(ageInTicks * 0.35F) * 0.08F;
        this.leftArm.zRot = -this.rightArm.zRot;
        this.hat.copyFrom(this.head);
    }
}
