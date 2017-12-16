'use strict';

var gulp = require('gulp');
var sass = require('gulp-sass');

gulp.task('sass', function () {
    return gulp.src('www/assets/scss/**/*.scss')
        .pipe(sass().on('error', sass.logError))
        .pipe(gulp.dest('www/assets/css'));
});

gulp.task('sass:watch', function () {
    gulp.watch('www/assets/scss/**/*.scss', ['sass']);
});