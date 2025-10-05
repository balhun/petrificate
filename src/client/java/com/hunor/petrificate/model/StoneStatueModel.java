package com.hunor.petrificate.model;

import com.hunor.petrificate.Petrificate;
import com.hunor.petrificate.entity.StoneStatueEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

import java.util.Random;

public class StoneStatueModel<T extends StoneStatueEntity> extends SinglePartEntityModel<T> {
	public static final EntityModelLayer STONESTATUE = new EntityModelLayer(Identifier.of(Petrificate.MOD_ID, "stonestatue"), "main");

	private final ModelPart root;
	private final ModelPart waist;
	private final ModelPart body;
	private final ModelPart head;
	private final ModelPart leftArm;
	private final ModelPart leftItem;
	private final ModelPart rightArm;
	private final ModelPart rightItem;
	private final ModelPart leftLeg;
	private final ModelPart rightLeg;

	public StoneStatueModel(ModelPart root) {
		this.root = root.getChild("root");
		this.waist = this.root.getChild("waist");
		this.body = this.waist.getChild("body");
		this.head = this.body.getChild("head");
		this.leftArm = this.body.getChild("leftArm");
		this.leftItem = this.leftArm.getChild("leftItem");
		this.rightArm = this.body.getChild("rightArm");
		this.rightItem = this.rightArm.getChild("rightItem");
		this.leftLeg = this.root.getChild("leftLeg");
		this.rightLeg = this.root.getChild("rightLeg");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();

		ModelPartData root = modelPartData.addChild("root", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

		ModelPartData waist = root.addChild("waist", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -12.0F, 0.0F));

		ModelPartData body = waist.addChild("body", ModelPartBuilder.create().uv(16, 16).cuboid(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -12.0F, 0.0F));

		ModelPartData head = body.addChild("head", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData leftArm = body.addChild("leftArm", ModelPartBuilder.create().uv(40, 16).cuboid(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(5.0F, 2.0F, 0.0F));

		ModelPartData leftItem = leftArm.addChild("leftItem", ModelPartBuilder.create(), ModelTransform.pivot(1.0F, 7.0F, 1.0F));

		ModelPartData rightArm = body.addChild("rightArm", ModelPartBuilder.create().uv(40, 16).cuboid(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(-5.0F, 2.0F, 0.0F));

		ModelPartData rightItem = rightArm.addChild("rightItem", ModelPartBuilder.create(), ModelTransform.pivot(-1.0F, 7.0F, 1.0F));

		ModelPartData leftLeg = root.addChild("leftLeg", ModelPartBuilder.create().uv(40, 16).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(1.9F, -12.0F, 0.0F));

		ModelPartData rightLeg = root.addChild("rightLeg", ModelPartBuilder.create().uv(0, 16).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(-1.9F, -12.0F, 0.0F));
		return TexturedModelData.of(modelData, 64, 64);
	}
	@Override
	public void setAngles(StoneStatueEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

		this.head.yaw = (float) Math.toRadians(netHeadYaw);
		this.head.pitch = (float) Math.toRadians(headPitch);


		this.rightArm.pitch = (float) Math.toRadians(entity.randomAngles); //limbSwingAmount * limbSwing; //entity.randomAngles > 0.4f ? (float) (45 * (Math.PI/180)) : (float) (-45 * (Math.PI / 180));
		this.rightArm.yaw = 0.0f;
		this.rightArm.roll = 0.0f;

		this.leftArm.pitch = (float) -Math.toRadians(entity.randomAngles); //-limbSwingAmount * limbSwing;//entity.randomAngles > 0.4f ? (float) (-45 * (Math.PI/180)) : (float) (45 * (Math.PI / 180));
		this.leftArm.yaw = 0.0f;
		this.leftArm.roll = 0.0f;

		this.rightLeg.pitch = (float) Math.toRadians(entity.randomAngles); //entity.randomAngles > 0.4f ? (float) (45 * (Math.PI/180)) : (float) (-45 * (Math.PI / 180));
		this.leftLeg.pitch = (float) -Math.toRadians(entity.randomAngles); //entity.randomAngles > 0.4f ? (float) (-45 * (Math.PI/180)) : (float) (45 * (Math.PI / 180));
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, int color) {
		root.render(matrices, vertices, light, overlay, color);
	}

	@Override
	public ModelPart getPart() { return root; }
}