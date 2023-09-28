import { cssBundleHref } from "@remix-run/css-bundle";
import type { LinksFunction, LoaderFunction } from "@remix-run/node";
import {
	Links,
	LiveReload,
	Meta,
	Outlet,
	Scripts,
	ScrollRestoration,
} from "@remix-run/react";

import { rootAuthLoader } from "@clerk/remix/ssr.server";
import { ClerkApp, V2_ClerkErrorBoundary } from "@clerk/remix";

import "@mantine/core/styles.css";
import { MantineProvider, ColorSchemeScript } from "@mantine/core";

export const links: LinksFunction = () => [
	...(cssBundleHref ? [{ rel: "stylesheet", href: cssBundleHref }] : []),
];

export const loader: LoaderFunction = (args) => rootAuthLoader(args);

/*
	export const loader: LoaderFunction = args => {
	  return rootAuthLoader(args, ({ request }) => {
	    const { sessionId, userId, getToken } = request.auth;
	    // fetch data
	    return { yourData: 'here' };
	  });
	};
*/
export const ErrorBoundary = V2_ClerkErrorBoundary();

function App() {
	return (
		<html lang="en">
			<head>
				<meta charSet="utf-8" />
				<meta name="viewport" content="width=device-width,initial-scale=1" />
				<Meta />
				<Links />
				<ColorSchemeScript />
			</head>
			<body>
				<MantineProvider>
					<Outlet />
					<ScrollRestoration />
					<Scripts />
					<LiveReload />
				</MantineProvider>
			</body>
		</html>
	);
}

export default ClerkApp(App);
