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
        MeshDefinition mesh = HumanoidModel.createMesh(new CubeDeformation(0.35F), 0.0F);
        PartDefinition root = mesh.getRoot();

        PartDefinition head = root.getChild("head");
        head.addOrReplaceChild("helmet_brow",
                CubeListBuilder.create().texOffs(0, 32)
                        .addBox(-4.5F, -8.5F, -5.0F, 9.0F, 3.0F, 2.0F),
                PartPose.ZERO);
        head.addOrReplaceChild("jaw_plate",
                CubeListBuilder.create().texOffs(22, 32)
                        .addBox(-4.0F, -0.5F, -5.2F, 8.0F, 3.0F, 2.0F),
                PartPose.ZERO);

        PartDefinition body = root.getChild("body");
        body.addOrReplaceChild("chest_plate",
                CubeListBuilder.create().texOffs(0, 40)
                        .addBox(-5.0F, 0.0F, -3.7F, 10.0F, 13.0F, 2.0F),
                PartPose.ZERO);
        body.addOrReplaceChild("back_plate",
                CubeListBuilder.create().texOffs(24, 40)
                        .addBox(-5.0F, 0.0F, 1.7F, 10.0F, 13.0F, 2.0F),
                PartPose.ZERO);
        body.addOrReplaceChild("belt",
                CubeListBuilder.create().texOffs(48, 40)
                        .addBox(-5.0F, 9.0F, -3.2F, 10.0F, 3.0F, 6.0F),
                PartPose.ZERO);

        PartDefinition rightArm = root.getChild("right_arm");
        rightArm.addOrReplaceChild("shoulder",
                CubeListBuilder.create().texOffs(0, 55)
                        .addBox(-5.4F, -3.0F, -3.2F, 5.0F, 5.0F, 5.0F),
                PartPose.ZERO);
        rightArm.addOrReplaceChild("gauntlet",
                CubeListBuilder.create().texOffs(20, 55)
                        .addBox(-4.0F, 6.5F, -3.2F, 4.0F, 4.0F, 5.0F),
                PartPose.ZERO);

        PartDefinition leftArm = root.getChild("left_arm");
        leftArm.addOrReplaceChild("shoulder",
                CubeListBuilder.create().texOffs(0, 55).mirror()
                        .addBox(0.4F, -3.0F, -3.2F, 5.0F, 5.0F, 5.0F).mirror(false),
                PartPose.ZERO);
        leftArm.addOrReplaceChild("gauntlet",
                CubeListBuilder.create().texOffs(20, 55).mirror()
                        .addBox(0.0F, 6.5F, -3.2F, 4.0F, 4.0F, 5.0F).mirror(false),
                PartPose.ZERO);

        PartDefinition rightLeg = root.getChild("right_leg");
        rightLeg.addOrReplaceChild("shin_guard",
                CubeListBuilder.create().texOffs(38, 55)
                        .addBox(-2.5F, 3.5F, -2.7F, 4.0F, 7.0F, 1.0F),
                PartPose.ZERO);

        PartDefinition leftLeg = root.getChild("left_leg");
        leftLeg.addOrReplaceChild("shin_guard",
                CubeListBuilder.create().texOffs(38, 55).mirror()
                        .addBox(-1.5F, 3.5F, -2.7F, 4.0F, 7.0F, 1.0F).mirror(false),
                PartPose.ZERO);

        return LayerDefinition.create(mesh, 80, 64);
    }

    @Override
    public void setupAnim(BruteZombie entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        super.setupAnim(entity, limbSwing, limbSwingAmount * 0.7F, ageInTicks, netHeadYaw, headPitch);

        float stomp = Mth.sin(limbSwing * 0.5F) * 0.06F * limbSwingAmount;
        this.body.zRot = Mth.sin(ageInTicks * 0.08F) * 0.03F;
        this.body.y = Math.abs(stomp) * 4.0F;
        this.head.y = this.body.y * 0.35F;
        this.rightLeg.xRot *= 0.75F;
        this.leftLeg.xRot *= 0.75F;
        this.rightArm.zRot += 0.10F;
        this.leftArm.zRot -= 0.10F;
        if (entity.isAggressive()) {
            this.rightArm.xRot -= 0.25F;
            this.leftArm.xRot -= 0.25F;
        }
        this.hat.copyFrom(this.head);
    }
}
