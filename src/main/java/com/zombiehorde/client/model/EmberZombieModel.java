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

        PartDefinition head = root.getChild("head");
        head.addOrReplaceChild("left_horn",
                CubeListBuilder.create().texOffs(32, 0)
                        .addBox(1.5F, -10.2F, -1.0F, 2.0F, 5.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.28F, 0.0F, 0.35F));
        head.addOrReplaceChild("right_horn",
                CubeListBuilder.create().texOffs(32, 0).mirror()
                        .addBox(-3.5F, -10.2F, -1.0F, 2.0F, 5.0F, 2.0F).mirror(false),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.28F, 0.0F, -0.35F));
        head.addOrReplaceChild("crown",
                CubeListBuilder.create().texOffs(40, 0)
                        .addBox(-2.0F, -11.0F, -0.5F, 4.0F, 2.0F, 1.0F),
                PartPose.ZERO);

        PartDefinition body = root.getChild("body");
        body.addOrReplaceChild("ember_core",
                CubeListBuilder.create().texOffs(0, 32)
                        .addBox(-2.0F, 4.0F, -3.4F, 4.0F, 4.0F, 1.0F),
                PartPose.ZERO);
        body.addOrReplaceChild("rib_spikes",
                CubeListBuilder.create().texOffs(10, 32)
                        .addBox(-5.0F, 2.0F, -2.8F, 1.0F, 7.0F, 5.0F)
                        .texOffs(22, 32)
                        .addBox(4.0F, 2.0F, -2.8F, 1.0F, 7.0F, 5.0F),
                PartPose.ZERO);
        body.addOrReplaceChild("back_spines",
                CubeListBuilder.create().texOffs(34, 32)
                        .addBox(-2.0F, 0.0F, 2.4F, 4.0F, 11.0F, 2.0F),
                PartPose.ZERO);

        PartDefinition rightArm = root.getChild("right_arm");
        rightArm.addOrReplaceChild("blade",
                CubeListBuilder.create().texOffs(46, 32)
                        .addBox(-4.8F, 1.0F, -3.3F, 2.0F, 7.0F, 1.0F),
                PartPose.ZERO);
        rightArm.addOrReplaceChild("wrist",
                CubeListBuilder.create().texOffs(52, 32)
                        .addBox(-4.2F, 8.5F, -3.0F, 2.0F, 3.0F, 4.0F),
                PartPose.ZERO);

        PartDefinition leftArm = root.getChild("left_arm");
        leftArm.addOrReplaceChild("blade",
                CubeListBuilder.create().texOffs(46, 32).mirror()
                        .addBox(2.8F, 1.0F, -3.3F, 2.0F, 7.0F, 1.0F).mirror(false),
                PartPose.ZERO);
        leftArm.addOrReplaceChild("wrist",
                CubeListBuilder.create().texOffs(52, 32).mirror()
                        .addBox(2.2F, 8.5F, -3.0F, 2.0F, 3.0F, 4.0F).mirror(false),
                PartPose.ZERO);

        return LayerDefinition.create(mesh, 64, 64);
    }

    @Override
    public void setupAnim(EmberZombie entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

        float heat = Mth.sin(ageInTicks * 0.25F) * 0.06F;
        float pulse = 0.18F + Math.max(0.0F, Mth.sin(ageInTicks * 0.35F)) * 0.10F;
        this.body.y = heat * 3.0F;
        this.head.y = heat * 1.6F;
        this.rightArm.zRot += pulse;
        this.leftArm.zRot -= pulse;
        this.rightArm.xRot -= 0.20F;
        this.leftArm.xRot -= 0.20F;
        this.body.xRot += heat * 0.35F;
        this.hat.copyFrom(this.head);
    }
}
