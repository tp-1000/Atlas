const gulp = require('gulp');
const watch = require('gulp-watch');
const browserSync = require('browser-sync').create();
const environments = require('gulp-environments');
const uglifycss = require('gulp-uglifycss');
const terser = require('gulp-terser');
const postcss = require('gulp-postcss');

const production = environments.production;

gulp.task('setup-css', () =>
    gulp.src(['src/main/resources/static/css/base-style.css'])
        .pipe(postcss([
            require('tailwindcss'),
            require('autoprefixer'),
        ]))
        .pipe(production(uglifycss()))
        .pipe(gulp.dest('src/main/resources/static/css/atlasCss/'))
);


gulp.task('watch', () => {
    browserSync.init({
        proxy: 'localhost:8080',
        browser: 'firefox'});
    gulp.watch(['src/main/resources/**/*.html'], gulp.series(reload));
    gulp.watch(['src/main/resources/**/*.css'], gulp.series(reload));
    gulp.watch(['src/main/resources/**/*.js'], gulp.series(reload));
});




gulp.task('copy-html', () => gulp.src(['src/main/resources/**/*.html']));

gulp.task('copy-css', () =>
    gulp.src(['src/main/resources/**/style.css'])
        .pipe(postcss([
            require('tailwindcss'),
            require('autoprefixer'),
        ]))
        .pipe(production(uglifycss()))
        .pipe(gulp.dest('target/classes'))
);

gulp.task('copy-js', () => gulp.src(['src/main/resources/**/*.js']).pipe(gulp.dest('target/classes/')));
/* not needed*/
gulp.task('copy-html-and-reload', gulp.series('copy-html', reload));
gulp.task('copy-css-and-reload', gulp.series('copy-css', reload));
gulp.task('copy-js-and-reload', gulp.series('copy-js', reload));

gulp.task('build', gulp.series('setup-css', 'copy-html', 'copy-css', 'copy-js'));
gulp.task('default', gulp.series('watch'));




function reload(done) {
    browserSync.reload();
    done();
}