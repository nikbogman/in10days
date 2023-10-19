import { getAuth } from "@clerk/remix/ssr.server";
import { LoaderFunction, json } from "@remix-run/node";
import { useLoaderData } from "@remix-run/react";

export const loader: LoaderFunction = async (args) => {
	const { getToken } = await getAuth(args);
	const token = await getToken();
	const res = await fetch("http://localhost:8080/events", {
		headers: {
			Authorization: `Bearer ${token}`,
		},
	});
	const data = await res.json();
	return json(data);
};

export default function ProtectedPage() {
	const data = useLoaderData<typeof loader>();
	console.log(data);
	return <h1>Well</h1>;
}
