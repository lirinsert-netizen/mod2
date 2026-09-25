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
        MeshDefinition mesh = HumanoidModel.createMesh(new CubeDeformation(-0.10F), 0.0F);
        PartDefinition root = mesh.getRoot();

        PartDefinition head = root.getChild("head");
        head.addOrReplaceChild("crest",
                CubeListBuilder.create().texOffs(32, 0)
                        .addBox(-1.0F, -10.5F, -0.8F, 2.0F, 5.0F, 2.0F),
                PartPose.ZERO);
        head.addOrReplaceChild("jaw_wrap",
                CubeListBuilder.create().texOffs(40, 0)
                        .addBox(-4.0F, -1.0F, -4.7F, 8.0F, 3.0F, 1.0F),
                PartPose.ZERO);

        PartDefinition body = root.getChild("body");
        body.addOrReplaceChild("chest_rig",
                CubeListBuilder.create().texOffs(0, 32)
                        .addBox(-4.5F, 1.0F, -2.7F, 9.0F, 7.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.16F, 0.0F, 0.0F));
        body.addOrReplaceChild("spine",
                CubeListBuilder.create().texOffs(20, 32)
                        .addBox(-1.0F, 0.0F, 2.0F, 2.0F, 10.0F, 2.0F),
                PartPose.offset(0.0F, 1.0F, 0.0F));
        body.addOrReplaceChild("belt",
                CubeListBuilder.create().texOffs(0, 40)
                        .addBox(-4.5F, 9.0F, -2.8F, 9.0F, 2.0F, 6.0F),
                PartPose.ZERO);

        PartDefinition rightArm = root.getChild("right_arm");
        rightArm.addOrReplaceChild("claw",
                CubeListBuilder.create().texOffs(30, 32)
                        .addBox(-2.8F, 11.5F, -4.2F, 4.0F, 2.0F, 2.0F),
                PartPose.ZERO);
        rightArm.addOrReplaceChild("strap",
                CubeListBuilder.create().texOffs(44, 32)
                        .addBox(-3.0F, 2.0F, -2.5F, 4.0F, 2.0F, 5.0F),
                PartPose.ZERO);

        PartDefinition leftArm = root.getChild("left_arm");
        leftArm.addOrReplaceChild("claw",
                CubeListBuilder.create().texOffs(30, 32).mirror()
                        .addBox(-1.2F, 11.5F, -4.2F, 4.0F, 2.0F, 2.0F).mirror(false),
                PartPose.ZERO);
        leftArm.addOrReplaceChild("strap",
                CubeListBuilder.create().texOffs(44, 32).mirror()
                        .addBox(-1.0F, 2.0F, -2.5F, 4.0F, 2.0F, 5.0F).mirror(false),
                PartPose.ZERO);

        PartDefinition rightLeg = root.getChild("right_leg");
        rightLeg.addOrReplaceChild("knee_guard",
                CubeListBuilder.create().texOffs(0, 48)
                        .addBox(-2.2F, 5.0F, -2.4F, 4.0F, 2.0F, 1.0F),
                PartPose.ZERO);
        rightLeg.addOrReplaceChild("ankle_wrap",
                CubeListBuilder.create().texOffs(10, 48)
                        .addBox(-1.9F, 10.0F, -2.2F, 3.0F, 2.0F, 4.0F),
                PartPose.ZERO);

        PartDefinition leftLeg = root.getChild("left_leg");
        leftLeg.addOrReplaceChild("knee_guard",
                CubeListBuilder.create().texOffs(0, 48).mirror()
                        .addBox(-1.8F, 5.0F, -2.4F, 4.0F, 2.0F, 1.0F).mirror(false),
                PartPose.ZERO);
        leftLeg.addOrReplaceChild("ankle_wrap",
                CubeListBuilder.create().texOffs(10, 48).mirror()
                        .addBox(-1.1F, 10.0F, -2.2F, 3.0F, 2.0F, 4.0F).mirror(false),
                PartPose.ZERO);

        return LayerDefinition.create(mesh, 64, 64);
    }

    @Override
    public void setupAnim(RunnerZombie entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

        float speed = Mth.clamp(limbSwingAmount, 0.0F, 1.0F);
        float bob = Mth.sin(limbSwing * 1.4F) * 0.06F * speed;
        float lean = 0.35F + speed * 0.35F;

        this.body.xRot = lean;
        this.head.xRot += -lean * 0.55F + bob * 0.7F;
        this.rightArm.xRot -= 0.55F + speed * 0.25F;
        this.leftArm.xRot -= 0.55F + speed * 0.25F;
        this.rightArm.zRot = 0.08F + Mth.cos(ageInTicks * 0.55F) * 0.08F;
        this.leftArm.zRot = -0.08F - Mth.cos(ageInTicks * 0.55F) * 0.08F;
        this.rightLeg.xRot *= 1.35F;
        this.leftLeg.xRot *= 1.35F;
        this.body.y = bob * 4.0F;
        this.head.y = this.body.y - bob * 2.0F;
        this.hat.copyFrom(this.head);
    }
}
