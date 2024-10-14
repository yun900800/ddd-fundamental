## self-bounding的本质是什么?

1.  This is the essence of CRG: The
    base class substitutes the derived class for its parameters. This means that the generic base
    class becomes a kind of template for common functionality for all its derived classes, but this
    functionality will use the derived type for all of its arguments and return values. That is, the
    exact type instead of the base type will be used in the resulting class.