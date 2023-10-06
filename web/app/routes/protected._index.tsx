import { getAuth } from "@clerk/remix/ssr.server";
import { LoaderFunction, json } from "@remix-run/node";
import { useLoaderData } from "@remix-run/react";

export const loader: LoaderFunction = async (args) => {
	const { getToken } = await getAuth(args);
	const token = await getToken();
	const res = await fetch("http://localhost:1323/protected", {
		headers: {
			Authorization: `Bearer ${token}`,
		},
	});
	const text = await res.text();
	return json({ text });
};

export default function ProtectedPage() {
	const data = useLoaderData<typeof loader>();
	return <h1>Well, {data.text}</h1>;
}
