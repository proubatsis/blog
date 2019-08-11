const Metalsmith = require('metalsmith');
const collections = require('metalsmith-collections');
const layouts = require('metalsmith-layouts');
const markdown = require('metalsmith-markdown');
const permalinks = require('metalsmith-permalinks');
const sass = require('metalsmith-sass');

Metalsmith(__dirname)
    .metadata({
        sitename: 'Panagiotis\' Blog',
        siteurl: 'blog.panagiotis.ca',
        searchapi: 'http://localhost:8080/api/search',
        useAnalytics: !!process.env.GOOGLE_ANALYTICS_TRACKING_ID,
        googleAnalyticsTrackingId: process.env.GOOGLE_ANALYTICS_TRACKING_ID,
    })
    .source('./src')
    .destination('./dist')
    .clean(true)
    .use(markdown())
    .use(collections({
        programming: 'posts/programming/*',
    }))
    .use(permalinks({
        linksets: [
            {
                match: { collection: 'programming' },
                pattern: 'programming/:slug',
            }
        ],
    }))
    .use(layouts())
    .use(sass({
        outputDir: 'css/',
    }))
    .build((err, files) => {
        if (err) throw err;
    });
