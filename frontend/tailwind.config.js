/** @type {import('tailwindcss').Config} */
module.exports = {
  presets: [require('@spartan-ng/brain/hlm-tailwind-preset')],
  content: [
    './src/**/*.{html,ts}',
    './src/app/shared/components/**/*.{html,ts}',
  ],
  theme: {
    extend: {
      colors: {
        primary: {
          100: '#f7efee',
          200: '#e8cbc9',
          300: '#d89b97',
          400: '#c96961',
          500: '#944c46',
          600: '#63312c',
          700: '#361815',
        },
        secondary: {
          100: '#dcf6f8',
          200: '#97d4d8',
          300: '#79abaf',
          400: '#5d8487',
          500: '#425f61',
          600: '#283c3e',
          700: '#111d1e',
        },
        gray: {
          DEFAULT: '#b0a9a8',
        },
      },
    },
  },
  plugins: [],
};

