package com.zombiehorde.client.model;

import com.zombiehorde.entity.PlagueZombie;
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

public final class PlagueZombieModel extends ZombieModel<PlagueZombie> {
    public PlagueZombieModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = HumanoidModel.createMesh(new CubeDeformation(0.05F), 0.0F);
        PartDefinition root = mesh.getRoot();

        PartDefinition head = root.getChild("head");
        head.addOrReplaceChild("hood",
                CubeListBuilder.create().texOffs(0, 32)
                        .addBox(-4.5F, -8.8F, -4.6F, 9.0F, 9.0F, 9.0F, new CubeDeformation(0.35F)),
                PartPose.ZERO);
        head.addOrReplaceChild("mask",
                CubeListBuilder.create().texOffs(36, 32)
                        .addBox(-2.5F, -2.0F, -5.1F, 5.0F, 4.0F, 2.0F),
                PartPose.ZERO);

        PartDefinition body = root.getChild("body");
        body.addOrReplaceChild("front_robe",
                CubeListBuilder.create().texOffs(0, 50)
                        .addBox(-4.5F, 0.0F, -2.8F, 9.0F, 12.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.08F, 0.0F, 0.0F));
        body.addOrReplaceChild("vials",
                CubeListBuilder.create().texOffs(20, 50)
                        .addBox(-4.9F, 2.0F, -2.9F, 2.0F, 6.0F, 2.0F)
                        .texOffs(28, 50)
                        .addBox(2.9F, 3.0F, -2.9F, 2.0F, 5.0F, 2.0F),
                PartPose.ZERO);
        body.addOrReplaceChild("back_tank",
                CubeListBuilder.create().texOffs(36, 50)
                        .addBox(-3.5F, 1.0F, 2.1F, 7.0F, 9.0F, 3.0F),
                PartPose.ZERO);

        PartDefinition rightArm = root.getChild("right_arm");
        rightArm.addOrReplaceChild("sleeve",
                CubeListBuilder.create().texOffs(56, 50)
                        .addBox(-3.5F, 2.0F, -2.8F, 4.0F, 6.0F, 5.0F),
                PartPose.ZERO);
        PartDefinition leftArm = root.getChild("left_arm");
        leftArm.addOrReplaceChild("sleeve",
                CubeListBuilder.create().texOffs(56, 50).mirror()
                        .addBox(-0.5F, 2.0F, -2.8F, 4.0F, 6.0F, 5.0F).mirror(false),
                PartPose.ZERO);

        return LayerDefinition.create(mesh, 80, 64);
    }

    @Override
    public void setupAnim(PlagueZombie entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

        float sway = Mth.sin(ageInTicks * 0.12F) * 0.10F;
        float cough = Math.max(0.0F, Mth.sin(ageInTicks * 0.25F)) * 0.03F;
        this.body.zRot = sway;
        this.body.xRot += cough;
        this.head.xRot += Mth.sin(ageInTicks * 0.09F) * 0.03F;
        this.rightArm.zRot += 0.12F + sway;
        this.leftArm.zRot -= 0.12F + sway;
        this.rightArm.xRot -= 0.10F;
        this.leftArm.xRot -= 0.10F;
        this.body.y = Math.abs(sway) * 2.0F;
        this.hat.copyFrom(this.head);
    }
}
