import { getAuth } from "@clerk/remix/ssr.server";
import { type LoaderFunction, redirect } from "@remix-run/node";
import { Outlet } from "@remix-run/react";

export const loader: LoaderFunction = async (args) => {
	const { userId } = await getAuth(args);
	if (!userId) {
		return redirect("/sign-in");
	}
	return {};
};

export default function ProtectedLayout() {
	return (
		<>
			<Outlet />
		</>
	);
}
