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

        root.getChild("head").addOrReplaceChild("hood",
                CubeListBuilder.create()
                        .texOffs(0, 32)
                        .addBox(-4.5F, -8.8F, -4.6F, 9.0F, 9.0F, 9.0F, new CubeDeformation(0.4F)),
                PartPose.ZERO);

        root.getChild("body").addOrReplaceChild("robe_front",
                CubeListBuilder.create()
                        .texOffs(36, 32)
                        .addBox(-4.5F, 0.0F, -2.8F, 9.0F, 12.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.08F, 0.0F, 0.0F));

        root.getChild("body").addOrReplaceChild("back_tubes",
                CubeListBuilder.create()
                        .texOffs(32, 0)
                        .addBox(-4.0F, 1.0F, 2.4F, 8.0F, 9.0F, 2.0F),
                PartPose.ZERO);

        return LayerDefinition.create(mesh, 64, 64);
    }

    @Override
    public void setupAnim(PlagueZombie entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

        this.body.zRot = 0.0F;

        float sway = Mth.sin(ageInTicks * 0.12F) * 0.08F;
        this.body.zRot = sway;
        this.rightArm.zRot += 0.08F + sway;
        this.leftArm.zRot -= 0.08F + sway;
        this.head.xRot += Mth.sin(ageInTicks * 0.09F) * 0.03F;
        this.hat.copyFrom(this.head);
    }
}
