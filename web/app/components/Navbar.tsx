export type NavbarProps = {};

import { Breadcrumbs, Anchor } from "@mantine/core";

const items = [
	{ title: "Events", href: "#" },
	{ title: "Event with id", href: "#" },
].map((item, index) => (
	<Anchor href={item.href} key={index} underline="hover">
		{item.title}
	</Anchor>
));

export function Navbar({}: NavbarProps) {
	return (
		<div>
			<Breadcrumbs>{items}</Breadcrumbs>
		</div>
	);
}
