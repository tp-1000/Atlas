module.exports = {
  future: {
    // removeDeprecatedGapUtilities: true,
    // purgeLayersByDefault: true,
  },
  purge: [],
  theme: {
    extend: {},
  },
  variants: {
      backgroundColor: ['responsive', 'group-hover', 'hover', 'focus'],
          borderColor: ['responsive', 'hover', 'focus', 'checked'],
              borderWidth: ['responsive', 'checked'],


  },
  plugins: [],
}
