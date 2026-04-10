# Design System Specification: The Digital Sanctuary

## 1. Overview & Creative North Star
The **Creative North Star** for this design system is **"The Digital Sanctuary."** 

For our older Indian demographic, technology should not feel like a complex tool, but like an organized, premium library. We are moving away from the "cluttered utility" look common in Indian apps. Instead, we embrace **High-End Editorialism**: a style characterized by vast breathing room, authoritative typography, and a "layered glass" depth that feels tangible and high-quality.

The system breaks the "template" look by using **intentional asymmetry**. For example, featured quotes may break the grid, and "Status Maker" highlights will use overlapping containers to create a sense of physical craft rather than digital flat-design.

---

## 2. Colors: Tonal Depth & The "No-Line" Rule
The palette is rooted in deep, midnight navies to reduce eye strain, with vibrant, culturally resonant accents.

### Surface Hierarchy & Nesting
We do not use lines to separate content. We use **Tonal Layering**.
- **Base Layer:** `surface` (#0b1326) – The foundation of the app.
- **Sectioning:** `surface_container_low` (#131b2e) – Use for large background sections to group content.
- **Primary Cards:** `surface_container_high` (#222a3d) – Standard card background.
- **Elevated Elements:** `surface_container_highest` (#2d3449) – Used for items that need to "pop" or for active states.

### The "No-Line" Rule
**Explicit Instruction:** Prohibit 1px solid borders for sectioning or card outlines. 
- Boundaries must be defined solely through background color shifts.
- If a card sits on the `surface`, it should be `surface_container`. If a button sits on a card, it uses a distinct accent color or a higher surface tier.

### The "Glass & Gradient" Rule
To achieve a premium "Editorial" feel, use **Glassmorphism** for floating action bars and navigation:
- Use `surface_variant` (#2d3449) at 60% opacity with a `20px` backdrop-blur.
- **Signature Textures:** For the "Status Maker" CTAs, use a linear gradient from `secondary` (#ffb95f) to `on_secondary_container` (#5b3800) at a 135-degree angle to provide "soul" and depth.

---

## 3. Typography: Authoritative Legibility
The system uses a pairing of **Plus Jakarta Sans** for headlines (to feel modern and premium) and **Inter** for body text (to ensure maximum legibility for Hindi and Telugu scripts).

- **Display (display-lg/md):** Reserved for hero quotes. Large, bold, and breathing.
- **Headline (headline-lg/md):** Used for category titles (e.g., "Good Morning Images"). High contrast against the dark background.
- **Title (title-lg/md):** Used for card titles. These must be legible at a glance for users with varying eyesight.
- **Body (body-lg):** The workhorse for quotes. Never go below `1rem` for body text to maintain accessibility for an older demographic.

**Multilingual Support:** When rendering Hindi or Telugu, increase the `line-height` by 20% compared to English to account for matras and complex glyphs, preventing visual "crowding."

---

## 4. Elevation & Depth: The Layering Principle
We avoid the "pasted-on" look of traditional material design.

- **Ambient Shadows:** When an element must float (like a FAB), use an extra-diffused shadow: `offset: 0, 12px; blur: 24px; color: rgba(6, 14, 32, 0.4)`. The shadow color should be a tinted version of `surface_container_lowest` to feel natural.
- **The "Ghost Border" Fallback:** If a border is required for accessibility (e.g., in a high-contrast mode), use `outline_variant` (#45464d) at **15% opacity**. Never use 100% opaque lines.
- **Soft Touch:** All interactive containers must use `roundedness-xl` (1.5rem / 24px) or `roundedness-lg` (1rem / 16px). This removes the "sharpness" of technology and makes the app feel approachable.

---

## 5. Components

### Cards & Lists (The Core Experience)
- **Rule:** Forbid divider lines.
- **Design:** Use `spacing-5` (1.7rem) as vertical white space to separate items.
- **Premium Detail:** For "Wallpaper" cards, use a subtle inner glow (`inner-shadow`) using `surface_bright` at 10% opacity to make the image feel "set into" the screen.

### Buttons (Touch-Friendly CTAs)
- **Primary (WhatsApp Share):** Use `primary` (#4ae176). High contrast, rounded-full. Ensure a minimum height of `spacing-12` (4rem) for easy tapping.
- **Secondary (Status Maker):** Use the Gold gradient (`secondary` to `secondary_container`). This signals "Premium/Special."
- **Tertiary:** Use `surface_container_highest` with `on_surface` text. No border.

### Chips (Category Filters)
- Use `surface_container_high`. Upon selection, transition to `primary` text with a `primary_container` background.
- Keep horizontal padding wide (`spacing-4`) to accommodate larger Indic text strings.

### Input Fields
- Avoid "Line-only" inputs. Use a solid `surface_container_low` background with a `roundedness-md` corner.
- **Helper Text:** Must be `body-md` in `on_surface_variant` to ensure it is large enough to read.

---

## 6. Do’s and Don’ts

### Do
- **DO** use the `spacing-8` (2.75rem) value for page margins. It creates a "luxury" feel by not crowding the screen edges.
- **DO** use high-contrast text (`on_surface` #dae2fd) for all quote content.
- **DO** label every icon. An older demographic prefers clarity over "minimalist" icon-only navigation.

### Don't
- **DON'T** use pure black (#000000). Use our `surface` navy to maintain a "midnight" premium feel.
- **DON'T** use 1px dividers. They create "visual noise" that confuses the hierarchy.
- **DON'T** use small touch targets. Every interactive element must be at least 48dp x 48dp, ideally 56dp+ for this demographic.
- **DON'T** crowd the "Status Maker" tool. Use `surface_container_highest` to create a focused, distraction-free "canvas" area.

---

## 7. Spacing Utility
*All layouts must snap to the following scale to maintain professional rhythm:*

- **Micro-adjustments:** `0.7rem` (spacing-2)
- **Standard Gutters:** `1.4rem` (spacing-4)
- **Section Breathing Room:** `3.5rem` (spacing-10)
- **Hero Margins:** `5.5rem` (spacing-16)