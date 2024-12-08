// Created by Anik Koirala and Sean Fang

// Package
package com.example.meepmeeptesting;

// Imports
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;

import org.rowlandhall.meepmeep.MeepMeep;
import org.rowlandhall.meepmeep.roadrunner.DefaultBotBuilder;
import org.rowlandhall.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class WHSAutonomous {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(500);

        // First possible robot positioning in quadrant 1
        RoadRunnerBotEntity quad1bot = new DefaultBotBuilder(meepMeep)
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .followTrajectorySequence(drive -> drive.trajectorySequenceBuilder(new Pose2d(12, 62, Math.toRadians(90)))
                        .lineTo(new Vector2d(45, 0))
                        .lineTo(new Vector2d(47, 60))
                        .lineTo(new Vector2d(48, 0))
                        .lineTo(new Vector2d(60, 60))
                        .lineTo(new Vector2d(55, 0))
                        .lineTo(new Vector2d(62, 0))
                        .lineTo(new Vector2d(60, 60))
                        .build());

        // Second possible robot positioning in quadrant 2
        RoadRunnerBotEntity quad2bot = new DefaultBotBuilder(meepMeep)
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .followTrajectorySequence(drive -> drive.trajectorySequenceBuilder(new Pose2d(-12, 62, Math.toRadians(90)))
                        .lineTo(new Vector2d(-45, 0))
                        .lineTo(new Vector2d(-47, 60))
                        .lineTo(new Vector2d(-48, 0))
                        .lineTo(new Vector2d(-60, 60))
                        .lineTo(new Vector2d(-55, 0))
                        .lineTo(new Vector2d(-62, 0))
                        .lineTo(new Vector2d(-60, 60))
                        .build());

        // Third possible robot positioning in quadrant 3
        RoadRunnerBotEntity quad3bot = new DefaultBotBuilder(meepMeep)
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .followTrajectorySequence(drive -> drive.trajectorySequenceBuilder(new Pose2d(-12, -62, Math.toRadians(90)))
                        .lineTo(new Vector2d(-45, 0))
                        .lineTo(new Vector2d(-47, -60))
                        .lineTo(new Vector2d(-48, 0))
                        .lineTo(new Vector2d(-60, -60))
                        .lineTo(new Vector2d(-55, 0))
                        .lineTo(new Vector2d(-62, 0))
                        .lineTo(new Vector2d(-60, -60))
                        .build());

        // Fourth possible robot positioning in quadrant 4
        RoadRunnerBotEntity quad4bot = new DefaultBotBuilder(meepMeep)
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .followTrajectorySequence(drive -> drive.trajectorySequenceBuilder(new Pose2d(12, -62, Math.toRadians(90)))
                        .lineTo(new Vector2d(45, 0))
                        .lineTo(new Vector2d(47, -60))
                        .lineTo(new Vector2d(48, 0))
                        .lineTo(new Vector2d(60, -60))
                        .lineTo(new Vector2d(55, 0))
                        .lineTo(new Vector2d(62, 0))
                        .lineTo(new Vector2d(60, -60))
                        .build());



        meepMeep.setBackground(MeepMeep.Background.FIELD_INTOTHEDEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(quad1bot)
                .addEntity(quad2bot)
                .addEntity(quad3bot)
                .addEntity(quad4bot)
                .start();
        // In total, ~13.07 seconds is spent during autonomous round
    }
}
