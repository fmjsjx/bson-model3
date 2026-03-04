require_relative '../property_generator'
require 'json'


class ObjectIdPropertyGenerator < PropertyGenerator

  def generate_field_declaration_code
    if required?
      "    private ObjectId #{name};\n"
    else
      "    private @Nullable ObjectId #{name};\n"
    end
  end

end
