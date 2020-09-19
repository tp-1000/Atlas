const gulp = require('gulp');
const watch = require('gulp-watch');
const browserSync = require('browser-sync').create();

gulp.task('watch', () => {
    browserSync.init({proxy: 'localhost:8080',});
    gulp.watch(['src/main/resources/**/*.html'], gulp.series('copy-html-and-reload'));
    gulp.watch(['src/main/resources/**/*.css'], gulp.series('copy-css-and-reload'));
    gulp.watch(['src/main/resources/**/*.js'], gulp.series('copy-js-and-reload'));
});

gulp.task('copy-html', () => gulp.src(['src/main/resources/**/*.html']).pipe(gulp.dest('target/classes/')));
gulp.task('copy-css', () => gulp.src(['src/main/resources/**/*.css']).pipe(gulp.dest('target/classes/')));
gulp.task('copy-js', () => gulp.src(['src/main/resources/**/*.js']).pipe(gulp.dest('target/classes/')));

gulp.task('copy-html-and-reload', gulp.series('copy-html', reload));
gulp.task('copy-css-and-reload', gulp.series('copy-css', reload));
gulp.task('copy-js-and-reload', gulp.series('copy-js', reload));

gulp.task('build', gulp.series('copy-html', 'copy-css', 'copy-js'));
gulp.task('default', gulp.series('watch'));

function reload(done) {
    browserSync.reload();
    done();
}