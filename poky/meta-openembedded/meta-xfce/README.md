This layer depends on:

URI: git://git.openembedded.org/openembedded-core
branch: scarthgap

URI: git://git.openembedded.org/meta-openembedded
branch: scarthgap

meta-xfce depends on meta-oe, meta-gnome and meta-multimedia in this repository.

To avoid dependencies on meta-multimedia you need to mask recipes by adding
this to local.conf:

BBMASK = "meta-xfce/recipes-multimedia"

Send pull requests to openembedded-devel@lists.openembedded.org with '[meta-xfce][scarthgap]' in the subject'

When sending single patches, please using something like:
git send-email -M -1 --to openembedded-devel@lists.openembedded.org --subject-prefix='meta-xfce][scarthgap][PATCH'

Layer maintainer: Armin Kuster <akuster808@gmail.com>
