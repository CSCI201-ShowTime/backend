## Backend Integration
This `README` provides information to integrate front-end with backend. 
There will be minimum changes on HTML files to preserve front-end design. 
Instead, communication logic will be presented as separate JavaScript files. 
Despite the best effort, in the case where an HTML file must be changed, 
a change log will be attached to the bottom of this list.

#### Version
Backend: v0.2.0a2
- Added login control and security to website. 
  Redirects unauthorized users to login page.
  Supports persistent login by saving local session Cookies and browser sessions. 
  User login state will be remembered until logout.

Front-end: 1f3eeba

#### General Instructions
1. Import MySQL database from `showtime.sql` file.
2. Open Java project.
3. Add Maven support if needed.
4. Verify database port, username, and password in `src/main/resources/application.properties`.
5. HTML/ CSS/ Javascript files are included.
   To use another copy, replace the files in `src/main/resources/static`.
   (Warning: `src/main/resources/static/js/server` includes server-client communication logic.)

For detailed instructions, check instructions for Demo on 
[Confluence](https://201fptesting3.atlassian.net/wiki/spaces/DOC/pages/229779/Demo+Installation+Guide).

#### Version Specific Instructions
`login.html`
- Included `login-server.js`.